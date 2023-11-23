package com.smallworld;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Transaction {

	public static void main(String[] args) throws IOException {
		
		String fullJson ="[{\"mtn\":663458,\"amount\":430.2,\"senderFullName\":\"Tom Shelby\",\"senderAge\":22,\"beneficiaryFullName\":\"Alfie Solomons\",\"beneficiaryAge\":33,\"issueId\":1,\"issueSolved\":false,\"issueMessage\":\"Looks like money laundering\"},{\"mtn\":1284564,\"amount\":150.2,\"senderFullName\":\"Tom Shelby\",\"senderAge\":22,\"beneficiaryFullName\":\"Arthur Shelby\",\"beneficiaryAge\":60,\"issueId\":2,\"issueSolved\":true,\"issueMessage\":\"Never gonna give you up\"},{\"mtn\":1284564,\"amount\":150.2,\"senderFullName\":\"Tom Shelby\",\"senderAge\":22,\"beneficiaryFullName\":\"Arthur Shelby\",\"beneficiaryAge\":60,\"issueId\":3,\"issueSolved\":false,\"issueMessage\":\"Looks like money laundering\"},{\"mtn\":96132456,\"amount\":67.8,\"senderFullName\":\"Aunt Polly\",\"senderAge\":34,\"beneficiaryFullName\":\"Aberama Gold\",\"beneficiaryAge\":58,\"issueId\":null,\"issueSolved\":true,\"issueMessage\":null},{\"mtn\":5465465,\"amount\":985,\"senderFullName\":\"Arthur Shelby\",\"senderAge\":60,\"beneficiaryFullName\":\"Ben Younger\",\"beneficiaryAge\":47,\"issueId\":15,\"issueSolved\":false,\"issueMessage\":\"Something's fishy\"},{\"mtn\":1651665,\"amount\":97.66,\"senderFullName\":\"Tom Shelby\",\"senderAge\":22,\"beneficiaryFullName\":\"Oswald Mosley\",\"beneficiaryAge\":37,\"issueId\":65,\"issueSolved\":true,\"issueMessage\":\"Never gonna let you down\"},{\"mtn\":6516461,\"amount\":33.22,\"senderFullName\":\"Aunt Polly\",\"senderAge\":34,\"beneficiaryFullName\":\"MacTavern\",\"beneficiaryAge\":30,\"issueId\":null,\"issueSolved\":true,\"issueMessage\":null},{\"mtn\":32612651,\"amount\":666,\"senderFullName\":\"Grace Burgess\",\"senderAge\":31,\"beneficiaryFullName\":\"Michael Gray\",\"beneficiaryAge\":58,\"issueId\":54,\"issueSolved\":false,\"issueMessage\":\"Something ain't right\"},{\"mtn\":32612651,\"amount\":666,\"senderFullName\":\"Grace Burgess\",\"senderAge\":31,\"beneficiaryFullName\":\"Michael Gray\",\"beneficiaryAge\":58,\"issueId\":78,\"issueSolved\":true,\"issueMessage\":\"Never gonna run around and desert you\"},{\"mtn\":32612651,\"amount\":666,\"senderFullName\":\"Grace Burgess\",\"senderAge\":31,\"beneficiaryFullName\":\"Michael Gray\",\"beneficiaryAge\":58,\"issueId\":99,\"issueSolved\":false,\"issueMessage\":\"Don't let this transaction happen\"},{\"mtn\":36448252,\"amount\":154.15,\"senderFullName\":\"Billy Kimber\",\"senderAge\":58,\"beneficiaryFullName\":\"Winston Churchill\",\"beneficiaryAge\":48,\"issueId\":null,\"issueSolved\":true,\"issueMessage\":null},{\"mtn\":645645111,\"amount\":215.17,\"senderFullName\":\"Billy Kimber\",\"senderAge\":58,\"beneficiaryFullName\":\"Major Campbell\",\"beneficiaryAge\":41,\"issueId\":null,\"issueSolved\":true,\"issueMessage\":null},{\"mtn\":45431585,\"amount\":89.77,\"senderFullName\":\"Billy Kimber\",\"senderAge\":58,\"beneficiaryFullName\":\"Luca Changretta\",\"beneficiaryAge\":46,\"issueId\":null,\"issueSolved\":true,\"issueMessage\":null}]";
		
		TransactionDataFetcher tdf = new TransactionDataFetcher();		

		System.out.println("Sum of the amounts of all transactions : "+ tdf.getTotalTransactionAmount(fullJson));
		
		System.out.println("Sum of the amounts of all transactions sent by: " + +tdf.getTotalTransactionAmountSentBy("Billy Kimber",fullJson));
		
		System.out.println("Highest transaction amount: " + tdf.getMaxTransactionAmount(fullJson));
		
		System.out.println("Number of unique clients that sent or received a transaction: "+ tdf.countUniqueClients(fullJson));
				
		System.out.println("Issue by: "+tdf.hasOpenComplianceIssues("Billy Kimber",fullJson));
		
		
		   Map<String, Object> result = tdf.getTransactionsByBeneficiaryName("Alfie Solomons", fullJson);

	        // Imprimir el resultado
	        System.out.println("Transactions for Alfie Solomons: " + result.get("Alfie Solomons"));
	}

}