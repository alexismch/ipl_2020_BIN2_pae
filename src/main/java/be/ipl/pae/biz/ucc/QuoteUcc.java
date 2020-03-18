package be.ipl.pae.biz.ucc;

import be.ipl.pae.biz.dto.QuoteDto;
import be.ipl.pae.biz.dto.UsersFilterDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface QuoteUcc {
  public ArrayList<QuoteDto> getQuotes() throws SQLException;
}
