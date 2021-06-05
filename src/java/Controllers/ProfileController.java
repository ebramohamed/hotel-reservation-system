
package Controllers;

import Services.HotelService;
import Services.RateService;
import Services.ReservationService;
import Services.RoomService;
import Services.UserService;
import Models.Hotel;
import Models.Reservation;
import Models.Room;
import Models.User;
import Models.Rating;
import Functions.Email;
import Functions.Hasher;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


@WebServlet(name = "profile", urlPatterns = {"/profile"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class ProfileController extends HttpServlet {

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
            out.println("<title>Servlet AdminController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminController at " + request.getContextPath() + "</h1>");
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

        List<Reservation> resevations = new ArrayList<Reservation>();
        ReservationService resevationDAO = new ReservationService();

        Hotel hotel = new Hotel();
        HotelService hotelDAO = new HotelService();

        User user = new User();
        UserService userDAO = new UserService();

        if (session.getAttribute("user_id") != null) {
            int userid = (Integer) session.getAttribute("user_id");

            user = userDAO.get(userid);
            resevations = resevationDAO.getAllWhere("user_id", userid);
            request.setAttribute("user", user);
            request.setAttribute("resevations", resevations);
            
            if (user.getUserType().equals("admin")) {
                
                
                RoomService roomDAO = new RoomService();
                List<Room> rooms = new ArrayList<Room>();

                RateService rateDAO = new RateService();
                List<Rating> userRate =  new ArrayList<Rating>();
                
                hotel = hotelDAO.getAllWhere("user_id", userid).get(0);
                rooms = roomDAO.getAllWhere("hotel_id", hotel.getId());
                userRate = rateDAO.getAllInner("hotel_id",  hotel.getId());
                
                String check_in = request.getParameter("check_in");
                String check_out = request.getParameter("check_out");
                
                
                resevations = resevationDAO.getRes(check_in, check_out, userid);
                
                request.setAttribute("hotel", hotel);
                request.setAttribute("rooms", rooms);
                request.setAttribute("rates", userRate);
                request.setAttribute("res", resevations);
                
            }

            

            RequestDispatcher rd = request.getRequestDispatcher("views/profile.jsp");
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("views/404.jsp");
            rd.forward(request, response);
        }
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

        List<Reservation> resevations = new ArrayList<Reservation>();
        ReservationService resevationDAO = new ReservationService();

        Hotel hotel = new Hotel();
        HotelService hotelDAO = new HotelService();

        User user = new User();
        UserService userDAO = new UserService();
        RoomService roomDAO = new RoomService();

        String reqType = request.getParameter("req_type");

        if (session.getAttribute("user_id") != null && reqType != null && !reqType.equals("")) {
            int userid = (Integer) session.getAttribute("user_id");
            user = userDAO.get(userid);
            resevations = resevationDAO.getAllWhere("user_id", userid);

            request.setAttribute("user", user);
            request.setAttribute("resevations", resevations);

            if (reqType.equals("update_profile")) {
                String firstName = request.getParameter("first_name");
                String lastName = request.getParameter("last_name");
                String email = request.getParameter("email");
                String phoneNumber = request.getParameter("phone_number");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");

                if (firstName.length() == 0
                        || lastName.length() == 0
                        || email.length() == 0
                        || phoneNumber.length() == 0
                        || password1.length() == 0) {

                    request.setAttribute("message", "Please enter all required data!");
                    RequestDispatcher rd = request.getRequestDispatcher("views/profile.jsp");
                    rd.forward(request, response);
                    return;
                }

                if (password1.equals(password2)) {
                    password1 = Hasher.getMd5(password1);

                    user.setId(userid);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setPhoneNumber(phoneNumber);
                    user.setPassword(password1);

                    int flag = userDAO.updateUser(user);

                    if (flag != 0) {
                        request.setAttribute("message", "Done!");
                        RequestDispatcher rd = request.getRequestDispatcher("views/profile.jsp");
                        rd.forward(request, response);
                        return;
                    }
                } else {
                    request.setAttribute("message", "Error!");
                    RequestDispatcher rd = request.getRequestDispatcher("views/profile.jsp");
                    rd.forward(request, response);
                    return;
                }
            } else if (reqType.equals("cancel_reservation")) {
                String reservationId = request.getParameter("reservation_id");
                String roomId = request.getParameter("room_id");
                int flage1 = resevationDAO.updateRowByColumn("status", "canceled", Integer.parseInt(reservationId));
                int flage2 = roomDAO.updateRowByColumn("is_available", "0", Integer.parseInt(roomId));

                System.out.println(Integer.parseInt(roomId));
                Room roomm = roomDAO.get(Integer.parseInt(roomId));
                System.out.println(roomm);
                hotel = hotelDAO.get(roomm.getHotelId());
                User admin = userDAO.get(hotel.getUserId());
                Email.send(admin.getEmail(), "Client cancellation", "Room ID: <b>" + roomId + "</b>, " + "Reservation ID: <b>" + reservationId + "</b>");

                response.sendRedirect("profile");
            } else if (reqType.equals("confirm_reservation")) {
                String reservationId = request.getParameter("reservation_id");
                String roomId = request.getParameter("room_id");
                String userId = request.getParameter("user_id");
                int flage1 = resevationDAO.updateRowByColumn("status", "confirmed", Integer.parseInt(reservationId));
                
                
                User userC = userDAO.get(Integer.parseInt(userId));
                
                Email.send(userC.getEmail(), "Admin confirmed", "Room ID: <b>" + roomId + "</b>, " + "Reservation ID: <b>" + reservationId + "</b>");

                response.sendRedirect("profile");
            } else if (reqType.equals("check_out")) {
               
                String roomId = request.getParameter("room_id");
                
                int flage2 = roomDAO.updateRowByColumn("is_available", "1", Integer.parseInt(roomId));

                

                

                response.sendRedirect("profile");
            } else if (reqType.equals("search")) {
                String email = request.getParameter("email");
                
                String[] keys = {"email"};
                String[] values = {email};
                
                User u = userDAO.getBy(keys, values);
                u.setPassword("");
                request.setAttribute("message", "Please enter all required data!");
                    RequestDispatcher rd = request.getRequestDispatcher("views/profile.jsp");
                    rd.forward(request, response);
                    return;
            } else if (reqType.equals("add_room")) {
                    String id = request.getParameter("id");
                    String number = request.getParameter("number");
                    String is_available = "1";
                    String type = request.getParameter("type");
                    String number_of_adults = request.getParameter("number_of_adults");
                    String number_of_children = request.getParameter("number_of_children");
                    String price_per_day = request.getParameter("price_per_day");
                    String facilities = request.getParameter("facilities");
                    
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    
                    Room aa = new Room();
                    aa.setHotelId(Integer.parseInt(id));
                    aa.setNumber(Integer.parseInt(number));
                    aa.setIsAvailable(true);
                    aa.setType(type);
                    aa.setNumberOfAdults(Integer.parseInt(number_of_adults));
                    aa.setNumberOfChildren(Integer.parseInt(number_of_children));
                    aa.setPricePerDay(Integer.parseInt(price_per_day));
                    aa.setFacilities(facilities);
                    aa.setCreationDate(formatter.format(date));
                    int f = roomDAO.save(aa);

                    if(f != 0)
                    {
                        
                        response.sendRedirect("profile");
                        return;
                    }
                    
   
            } else if (reqType.equals("update_hotel")) {
                String hotel_id = request.getParameter("hotel_id");
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String phone_number = request.getParameter("phone_number");
                String stars = request.getParameter("stars");
                String address = request.getParameter("address");
                String governorate = request.getParameter("governorate");
                String city = request.getParameter("city");
                String zipcode = request.getParameter("zipcode");
                String longitude = request.getParameter("longitude");
                String latitude = request.getParameter("latitude");

                if (name.length() == 0
                        || description.length() == 0
                        || phone_number.length() == 0
                        || stars.length() == 0
                        || address.length() == 0
                        || governorate.length() == 0
                        || city.length() == 0
                        || zipcode.length() == 0
                        || longitude.length() == 0
                        || latitude.length() == 0) {

                    request.setAttribute("message", "Please enter all required data!");
                    RequestDispatcher rd = request.getRequestDispatcher("views/profile.jsp");
                    rd.forward(request, response);
                    return;
                }

                hotel.setId(Integer.parseInt(hotel_id));
                hotel.setName(name);
                hotel.setDescription(description);
                hotel.setPhoneNumber(phone_number);
                hotel.setStars(Integer.parseInt(stars));
                hotel.setAddress(address);
                hotel.setGovernorate(governorate);
                hotel.setCity(city);
                hotel.setZipcode(zipcode);
                hotel.setLongitude(Double.parseDouble(longitude));
                hotel.setLatitude(Double.parseDouble(latitude));

                int flag = hotelDAO.updateHotel(hotel);

                if (flag != 0) {

                    response.sendRedirect("profile");
                    return;
                }
            } else if (reqType.equals("update_picture")) {
                /* Receive file uploaded to the Servlet from the HTML5 form */
                final String hotel_id = request.getParameter("hotel_id");
                final Part filePart = request.getPart("file");
                final String fileName = filePart.getSubmittedFileName();

                OutputStream out = null;
                InputStream filecontent = null;
                final PrintWriter writer = response.getWriter();

                try {
                    out = new FileOutputStream(new File("C:\\Users\\WIN\\Documents\\NetBeansProjects\\HotelReservationSystem\\web\\views\\uploads" + File.separator
                            + fileName));
                    filecontent = filePart.getInputStream();

                    int read = 0;
                    final byte[] bytes = new byte[1024];

                    while ((read = filecontent.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    
                    int flag = hotelDAO.updateRowByColumn("image_path", fileName,Integer.parseInt(hotel_id));

                    if (flag != 0) {

                        response.sendRedirect("profile");
                        return;
                    }else
                    {
                        request.setAttribute("message", "Please enter all required data!");
                        RequestDispatcher rd = request.getRequestDispatcher("views/profile.jsp");
                        rd.forward(request, response);
                    }

                } catch (FileNotFoundException fne) {
                    writer.println("You either did not specify a file to upload or are "
                            + "trying to upload a file to a protected or nonexistent "
                            + "location.");
                    writer.println("<br/> ERROR: " + fne.getMessage());

                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (filecontent != null) {
                        filecontent.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                }
            }

        } else {
            RequestDispatcher rd = request.getRequestDispatcher("views/404.jsp");
            rd.forward(request, response);
        }
    }

    private String retrieveFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String str : items) {
            if (str.trim().startsWith("filename")) {
                return str.substring(str.indexOf("=") + 2, str.length() - 1);
            }
        }
        return "";
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
