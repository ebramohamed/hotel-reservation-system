
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


import Services.UserService;
import Models.User;
import Functions.Email;
import Functions.AlphaNumericString;
import Functions.Hasher;
import Functions.GoogleCaptcha;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

@WebServlet(name = "register", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet
{
    public UserService userDAO = null;
    public User user = null;
    public RegisterController()
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
            RequestDispatcher rd = request.getRequestDispatcher("views/register.jsp");  
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
        String errMessage = "";
        boolean isValid = true;
        
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name"); 
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone_number");
        String type = request.getParameter("type");
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        
        String password = AlphaNumericString.getAlphaNumericString(10);
        String hashedPassword = Hasher.getMd5(password);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        
        this.user.setFirstName(firstName);
        this.user.setLastName(lastName);
        this.user.setUsername(username);
        this.user.setEmail(email);
        this.user.setPhoneNumber(phoneNumber);
        this.user.setUserType(type);
        this.user.setPassword(hashedPassword);
        this.user.setCreationDate(formatter.format(date));
        
        if(!GoogleCaptcha.verify(gRecaptchaResponse) && isValid)
        {
            errMessage = "Captcha invalid!";
            isValid = false;
        }
        
        if((firstName.length() == 0 || 
           lastName.length() == 0 ||
           username.length() == 0 ||
           email.length() == 0 ||
           phoneNumber.length() == 0 ||
           type.length() == 0) && isValid)
        {
            errMessage = "Enter a valid data!";
            isValid = false;
        }
        
        String[] keys = {"email"};
        String[] values = {email};
        User userExists = this.userDAO.getBy(keys, values);
        if(userExists != null && isValid)
        {
            errMessage = "Email address exists";
            isValid = false;
        }
        
        keys[0] = "username";
        values[0] = username;
        userExists = this.userDAO.getBy(keys, values);
        
        if(userExists != null && isValid)
        {
            errMessage = "Username address exists";
            isValid = false;
        }
        
        
        int flag = 1;
        if(isValid)
        {
            flag = this.userDAO.save(this.user);
            
            if(flag != 0)
            {
                HttpSession session = request.getSession();  
                session.setAttribute("user_id", this.user.getId());
                session.setAttribute("email", this.user.getEmail());
                session.setAttribute("user_type", this.user.getUserType());

                Email.send(this.user.getEmail(), "Thanks for registering", "Your password: <b>" + password + "</b>");
                
                response.sendRedirect("profile");
            }
            
        }
        
        if(!isValid || flag == 0)
        {
            request.setAttribute("message", errMessage);
            RequestDispatcher rd=request.getRequestDispatcher("views/register.jsp");  
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
