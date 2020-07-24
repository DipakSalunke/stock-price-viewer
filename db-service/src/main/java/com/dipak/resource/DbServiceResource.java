package com.dipak.resource;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dipak.model.Quote;
import com.dipak.model.Quotes;
import com.dipak.repository.QuotesRepository;

@RestController
@RequestMapping("/rest/db")
public class DbServiceResource {
		
	private QuotesRepository quotesRepository;
	
	public DbServiceResource(QuotesRepository quotesRepository) {
		super();
		this.quotesRepository = quotesRepository;
	}

	@GetMapping("/{username}")
	public List<String> getQuotes(@PathVariable("username") final String username){
		
		return getQuotesByUserName(username);
		
	}
	
public List<String> getQuotesByUserName(@PathVariable("username") final String username){
		
		return quotesRepository.findByUserName(username)
		.stream()
		.map(Quote::getQuote)
		.collect(Collectors.toList());
		
	}
	
	@PostMapping("/add")
	public List<String> add(@RequestBody final Quotes quotes){
		quotes.getQuotes()
		.stream()
		.map(quote -> new Quote( quotes.getUserName(),quote))
		.forEach(quote -> quotesRepository.save(quote));
		
		return getQuotesByUserName(quotes.getUserName());
		}
	
	
	@PostMapping("/delete/{username}")
	public List<String> delete(@PathVariable("username") final String username){
		List<Quote> quotes = quotesRepository.findByUserName(username);
		quotesRepository.deleteAll(quotes);
		return getQuotesByUserName(username);
		
	}
	
	}
