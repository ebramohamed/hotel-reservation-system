
package Controllers;



import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Functions.Hasher;
import Services.UserService;
import Models.User;
import Functions.GoogleCaptcha;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginController extends HttpServlet 
{
    public UserService userDAO = null;
    public User user = null;
    public LoginController()
    {
        this.userDAO = new UserService();
        this.user = new User();
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
        HttpSession session = request.getSession();
        
        
        if (session.getAttribute("user_id") == null) 
        {
            RequestDispatcher rd = request.getRequestDispatcher("views/login.jsp");  
            rd.forward(request, response);
        }
        else
        {
            response.sendRedirect("profile");
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
        String email = request.getParameter("email");
        String password = request.getParameter("password"); 
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        password = Hasher.getMd5(password);
        
        if(!GoogleCaptcha.verify(gRecaptchaResponse))
        {
            request.setAttribute("message", "Captcha invalid!");
            RequestDispatcher rd=request.getRequestDispatcher("views/login.jsp");  
            rd.forward(request, response);
        }
        
        String[] keys = {"email", "password"};
        String[] values = {email, password};
        this.user = this.userDAO.getBy(keys, values);
        
        if(email.length() == 0 || password.length() == 0 || this.user == null)
        {
            request.setAttribute("message", "Enter a valid email and password!");
            RequestDispatcher rd = request.getRequestDispatcher("views/login.jsp");  
            rd.forward(request, response);
        }
        else 
        {
            HttpSession session = request.getSession();  
            session.setAttribute("user_id", this.user.getId());
            session.setAttribute("email", this.user.getEmail());
            session.setAttribute("user_type", this.user.getUserType());
            
            response.sendRedirect("profile");
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
