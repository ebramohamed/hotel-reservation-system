
package Controllers;

import Services.UserService;
import Models.User;
import Functions.Hasher;
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
import javax.servlet.http.HttpSession;


@WebServlet(name = "settings", urlPatterns = {"/settings"})
public class SettingsController extends HttpServlet
{



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
        HttpSession session = request.getSession(false);
        User user = new User();
        UserService userDAO = new UserService();
        
        if(session.getAttribute("user_id") != null)
        {
            int userid = (Integer) session.getAttribute("user_id");
            user = userDAO.get(userid);
        
            request.setAttribute("user", user);
            RequestDispatcher rd = request.getRequestDispatcher("views/settings.jsp");  
            rd.forward(request, response);
        }
        else
        {
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
            throws ServletException, IOException
    {
        HttpSession session = request.getSession(false);
        User user = new User();
        UserService userDAO = new UserService();
        
        if(session.getAttribute("user_id") != null)
        {
            int userid = (Integer) session.getAttribute("user_id");
            user = userDAO.get(userid);
        
            request.setAttribute("user", user);
            
        }
        else
        {
            RequestDispatcher rd = request.getRequestDispatcher("views/404.jsp");
            rd.forward(request, response);
        }
        
       
        
        if(session.getAttribute("user_id") != null)
        {
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phone_number");
            String password1 = request.getParameter("password1");
            String password2 = request.getParameter("password2");
            
            if(firstName.length() == 0 || 
               lastName.length() == 0 || 
               email.length() == 0 || 
               phoneNumber.length() == 0 || 
               password1.length() == 0)
            {
                
                request.setAttribute("message", "Please enter all required data!");
                RequestDispatcher rd = request.getRequestDispatcher("views/settings.jsp");  
                rd.forward(request, response);
                return;
            }
            
            if(password1.equals(password2))
            {
                password1 = Hasher.getMd5(password1);
                int userid = (Integer) session.getAttribute("user_id");
                
                user.setId(userid);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhoneNumber(phoneNumber);
                user.setPassword(password1);
                
                int flag = userDAO.updateUser(user);
                
                if(flag != 0)
                {
                    request.setAttribute("message", "Done!");
                    RequestDispatcher rd = request.getRequestDispatcher("views/settings.jsp");  
                    rd.forward(request, response);
                    return;
                }
                
                
            }
            else
            {
                request.setAttribute("message", "Error!");
                RequestDispatcher rd = request.getRequestDispatcher("views/settings.jsp");  
                rd.forward(request, response);
                return;
            }
            
        
            
            
            
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
