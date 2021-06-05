
package Controllers;

import Services.HotelService;
import Services.RateService;
import Services.ReservationService;
import Services.RoomService;
import Services.SearchService;
import Models.Hotel;
import Models.Reservation;
import Models.Room;
import Models.Rating;
import Functions.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "cart", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    public HotelService hotelDAO = null;
    public Hotel hotel = null;
    
    public RoomService roomDAO = null;
    public List<Room> rooms = null;
    
    public RateService rateDAO = null;
    public List<Rating> userRate = null;
    
    public ReservationService reservationDAO = null;
    public Reservation reservation = null;
    
    public CartController()
    {
        this.hotelDAO = new HotelService();
        this.hotel = new Hotel();
        
        this.roomDAO = new RoomService();
        this.rooms = new ArrayList<Room>();
        
        this.rateDAO = new RateService();
        this.userRate = new ArrayList<Rating>();
        
        this.reservationDAO = new ReservationService();
        this.reservation = new Reservation();
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet cartController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet cartController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        SearchService searchDAO = new SearchService();
        
        String hotelId = request.getParameter("id");
        String checkInDate = request.getParameter("check_in");
        String checkOutDate = request.getParameter("check_out");
        String adults = request.getParameter("adults");
        String children = request.getParameter("children");
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long diff = 1;
        try {
            Date dateStart = simpleDateFormat.parse(checkInDate);
            Date dateEnd = simpleDateFormat.parse(checkOutDate);
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        if(session.getAttribute("user_id") == null || hotelId == null || hotelId.equals("") || diff <= 0 )
        {
            request.setAttribute("message_cart", "Please enter valid data!");
            RequestDispatcher rd = request.getRequestDispatcher("hotel?id=" + hotelId);
            rd.forward(request, response);
            return;
        }
        
        this.rooms = searchDAO.getRoomsBy(checkInDate, checkOutDate, adults, children, Integer.parseInt(hotelId));

        if(this.rooms.size() <= 0)
        {
            request.setAttribute("message_cart", "NO Rooms available!");
            RequestDispatcher rd = request.getRequestDispatcher("hotel?id=" + hotelId);
            rd.forward(request, response);
            return;
        }
        
        
        
        request.setAttribute("nights", diff);
        request.setAttribute("total_cost", this.rooms.get(0).getPricePerDay() * diff);
        request.setAttribute("room", this.rooms.get(0));
        System.out.println(this.rooms.get(0));
        RequestDispatcher rd = request.getRequestDispatcher("views/cart_hotel.jsp");  
        rd.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        SearchService searchDAO = new SearchService();
        
        String roomId = request.getParameter("room_id");
        String checkInDate = request.getParameter("check_in");
        String checkOutDate = request.getParameter("check_out");
        String cardNumber = request.getParameter("card_number");
        
        
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long diff = 1;
        try {
            Date dateStart = simpleDateFormat.parse(checkInDate);
            Date dateEnd = simpleDateFormat.parse(checkOutDate);
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        if(session.getAttribute("user_id") == null || roomId == null || roomId.equals("") || diff <= 0 )
        {
            request.setAttribute("message_cart", "Please enter valid data!");
            RequestDispatcher rd = request.getRequestDispatcher("views/404.jsp");
            rd.forward(request, response);
            return;
        }
        
        
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

       
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
        reservation.setRoomId(Integer.parseInt(roomId));
        reservation.setStatus("pending");
        reservation.setUserId(Integer.parseInt(session.getAttribute("user_id").toString()));
        reservation.setCreationDate(formatter.format(date));
        
        int flag = reservationDAO.save(reservation);

        if(flag != 0)
        {
            roomDAO.updateRowByColumn("is_available", "0", Integer.parseInt(roomId));
            Email.send(session.getAttribute("email").toString(), "Thank you for booking", reservation.toString());
            request.setAttribute("done", "done");
            RequestDispatcher rd = request.getRequestDispatcher("views/confirmation.jsp");  
            rd.forward(request, response);
        }
        
        
        
        
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
