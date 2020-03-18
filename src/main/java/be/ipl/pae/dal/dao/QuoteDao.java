package be.ipl.pae.dal.dao;

import be.ipl.pae.biz.dto.QuoteDto;

import java.sql.SQLException;
import java.util.List;

import be.ipl.pae.biz.dto.QuoteDto;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface QuoteDao {

  List<QuoteDto> getAllQuote() throws SQLException;

  QuoteDto insertQuote(QuoteDto quoteDto);

  QuoteDto createQuoteDto(ResultSet res) throws SQLException;
}
