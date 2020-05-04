package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.ihm.servlets.utils.Util.hasAccess;
import static be.ipl.pae.ihm.servlets.utils.Util.verifyNotEmpty;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.objets.UserStatus;
import be.ipl.pae.biz.ucc.CustomerUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerServlet extends AbstractServlet {

  @Injected
  private CustomerUcc customerUcc;

  @Injected
  private DtoFactory dtoFactory;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, UserStatus.CUSTOMER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    String idUserString = req.getParameter("userId");

    int idUser;
    if (idUserString != null) {
      try {
        idUser = Integer.parseInt(idUserString);
      } catch (NumberFormatException ex) {
        sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED,
            "L'id d'utilisteur passé en paramètre n'est pas un nombre entier");
        return;
      }
    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED,
          "Un id d'utilisteur doit être passé en paramètre");
      return;
    }

    System.out.println("idUser=" + idUser);
    CustomerDto customerDto = customerUcc.getCustomerByIdUser(idUser);
    sendSuccessWithJson(resp, "customer", createGensonBuilder().create().serialize(customerDto));
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("POST /api/customer by " + req.getRemoteAddr());

    String token = (String) req.getSession().getAttribute("token");
    if (!hasAccess(token, UserStatus.WORKER)) {
      sendError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Wrong token.");
      return;
    }

    String lastname = req.getParameter("lastname");
    String firstname = req.getParameter("firstname");
    String address = req.getParameter("address");
    String postalCodeString = req.getParameter("postalCode");
    String city = req.getParameter("city");
    String email = req.getParameter("email");
    String phoneNumber = req.getParameter("phoneNumber");

    if (verifyNotEmpty(lastname, firstname, address, postalCodeString, city, email, phoneNumber)) {
      try {
        int postalCode = Integer.parseInt(postalCodeString);

        CustomerDto customerToInsert = dtoFactory.getCustomer();

        customerToInsert.setFirstName(firstname);
        customerToInsert.setLastName(lastname);
        customerToInsert.setAddress(address);
        customerToInsert.setPostalCode(postalCode);
        customerToInsert.setCity(city);
        customerToInsert.setEmail(email);
        customerToInsert.setPhoneNumber(phoneNumber);

        customerUcc.insert(customerToInsert);

        sendSuccess(resp);
      } catch (BizException bizE) {
        sendError(resp, HttpServletResponse.SC_CONFLICT, bizE.getMessage());
      } catch (NumberFormatException ex) {
        sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Code postal invalide");
      } catch (Exception ex) {
        sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
      }
    } else {
      sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED, "Paramètres invalides");
    }
  }
}
