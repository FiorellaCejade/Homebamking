package com.mindhub.homebaking;
import com.mindhub.homebaking.models.*;
import com.mindhub.homebaking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebakingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebakingApplication.class, args);}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository , AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {

			Client Melba = new Client("Melba", "Lorenzo","melba@mindHub.com", passwordEncoder.encode("1"));
			Client Tomas = new Client("Tomas", "Almiron","tomasAlmiron@hotmail.com", passwordEncoder.encode("123"));
			Client admin = new Client("admin", "admin", "administrador@gmail.com", passwordEncoder.encode("admin"));
			// save a couple of customers
			clientRepository.save(Melba);
			clientRepository.save(Tomas);
			clientRepository.save(admin);

//			Account accountUno = new Account( "VIN001", 5000, LocalDateTime.now(), Melba );
//			accountRepository.save(accountUno);
//
//			Transaction transactionOne = new Transaction(3000, TransactionType.CREDIT, LocalDateTime.now(), "Supermarket", accountUno);
//			transactionRepository.save(transactionOne);
//			Transaction transactionThree = new Transaction(2500, TransactionType.DEBIT, LocalDateTime.now(), "Hairdressing", accountUno);
//			transactionRepository.save(transactionThree);
//			Transaction transactionFour = new Transaction(3650, TransactionType.CREDIT, LocalDateTime.now(), "phone", accountUno);
//			transactionRepository.save(transactionFour);
//			Transaction transactionSeven = new Transaction(3000, TransactionType.CREDIT, LocalDateTime.now(), "Supermercado", accountUno);
//			transactionRepository.save(transactionSeven);


			Loan loanOne = new Loan("Mortgage", 500000, List.of(12,24,36,48,60), 1.50);
			loanRepository.save(loanOne);
			Loan loanTwo = new Loan("Personal", 100000, List.of( 6,12,24), 1.30);
			loanRepository.save(loanTwo);
			Loan loanTree = new Loan("Automotive", 300000, List.of(6,12,24,36), 1.40);
			loanRepository.save(loanTree);

//			Card cardOne = new Card( Melba, CardType.DEBIT, ColorType.GOLD,Melba.getFirstName() + Melba.getLastName(),"4859 3256 7821 6942", 222, LocalDate.now(), LocalDate.now().plusYears(5));
//			cardRepository.save(cardOne);
//			Card cardTwo = new Card( Melba, CardType.CREDIT, ColorType.TITANIUM,"Melba Lorenzo","6589 5234 3218 9478", 124, LocalDate.now(), LocalDate.now().plusYears(5));
//			cardRepository.save(cardTwo);
//			Card cardTree = new Card( Melba, CardType.DEBIT, ColorType.SILVER,"Melba Lorenzo","4859 3973 7578 4392", 265, LocalDate.now(), LocalDate.now().plusYears(5));
//			cardRepository.save(cardTree);

		};
	};

};



