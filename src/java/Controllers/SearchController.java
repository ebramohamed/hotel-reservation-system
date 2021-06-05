
package Controllers;

import Services.HotelService;
import Services.SearchService;
import Models.Hotel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "search", urlPatterns = {"/search"})
public class SearchController extends HttpServlet
{
    public HotelService hotelDAO = null;
    public Hotel hotel = null;
    public SearchController()
    {
        this.hotelDAO = new HotelService();
        this.hotel = new Hotel();
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
            throws ServletException, IOException
    {
        List<Hotel> hotels = new ArrayList<Hotel>();
        SearchService searchDAO = new SearchService();
        
        String checkInDate = request.getParameter("check_in");
        String checkOutDate = request.getParameter("check_out");
        String adults = request.getParameter("adults");
        String children = request.getParameter("children");
        String governorate = request.getParameter("governorate");
        String hotelName = request.getParameter("hotel_name");
        
        hotels = searchDAO.getAllBy(checkInDate, checkOutDate, adults, children, governorate, hotelName);
        
        request.setAttribute("hotels", hotels);
        RequestDispatcher rd = request.getRequestDispatcher("views/all_hotels_list.jsp");  
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
