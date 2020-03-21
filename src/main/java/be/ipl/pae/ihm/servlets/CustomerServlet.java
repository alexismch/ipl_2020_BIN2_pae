package be.ipl.pae.ihm.servlets;

import static be.ipl.pae.util.Util.verifyNotEmpty;

import be.ipl.pae.biz.dto.CustomerDto;
import be.ipl.pae.biz.objets.DtoFactory;
import be.ipl.pae.biz.ucc.CustomerUcc;
import be.ipl.pae.dependencies.Injected;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerServlet extends AbstractServlet {

  @Injected
  CustomerUcc customerUcc;

  @Injected
  DtoFactory dtoFactory;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    System.out.println("POST /api/customer by " + req.getRemoteAddr());

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

        customerUcc.insert(customerToInsert);

        sendSuccess(resp);
      } catch (FatalException fatalE) {
        fatalE.printStackTrace();
        sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, fatalE.getMessage());
      } catch (BizException bizE) {
        bizE.printStackTrace();
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
