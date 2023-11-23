package com.smallworld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionDataFetcher {

	/**
	 * Returns the sum of the amounts of all transactions
	 */
	public double getTotalTransactionAmount(String transaction) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(transaction);

		double amounts = 0.0;
		if (jsonNode.isArray()) {
			List<JsonNode> transactionInJson = jsonNode.findValues("amount");

			for (JsonNode transaccion : transactionInJson) {
				amounts += transaccion.asDouble();
			}
		}
		return amounts;
	}

	/**
	 * Returns the sum of the amounts of all transactions sent by the specified
	 * client
	 */
	public double getTotalTransactionAmountSentBy(String senderFullName, String json) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(json);

		double amounts = 0.0;

		if (jsonNode.isArray()) {
			List<JsonNode> transactionInJson = jsonNode.findValues("senderFullName");

			for (int i = 0; i < transactionInJson.size(); i++) {
				if (transactionInJson.get(i).asText().equals(senderFullName)) {
					JsonNode amountNode = jsonNode.get(i).get("amount");
					amounts += amountNode.asDouble();
				}
			}
		}

		return amounts;
	}

	/**
	 * Returns the highest transaction amount
	 */
	public double getMaxTransactionAmount(String json) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(json);

		if (jsonNode.isArray()) {
			List<Double> amounts = jsonNode.findValuesAsText("amount").stream().map(Double::parseDouble).toList();

			return amounts.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
		}
		return 0.0;
	}

	/**
	 * Counts the number of unique clients that sent or received a transaction
	 */
	public long countUniqueClients(String json) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(json);

		if (jsonNode.isArray()) {
			Set<String> uniqueClients = new HashSet<>();

			jsonNode.findValuesAsText("senderFullName").stream().forEach(uniqueClients::add);
			jsonNode.findValuesAsText("beneficiaryFullName").stream().forEach(uniqueClients::add);

			return uniqueClients.size();
		}

		return 0;
	}

	/**
	 * Returns whether a client (sender or beneficiary) has at least one transaction
	 * with a compliance issue that has not been solved
	 */
	public boolean hasOpenComplianceIssues(String clientFullName, String json) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(json);
		boolean flag = false;

		if (jsonNode.isArray()) {
			List<JsonNode> listJsonSender = objectMapper.readTree(json).findParents("senderFullName").stream()
					.filter(parentNode -> parentNode.get("senderFullName").textValue().equals(clientFullName)).toList();

			for (JsonNode nodo : listJsonSender) {
				JsonNode issueSolvedNode = nodo.get("issueSolved");
				if (issueSolvedNode != null && !issueSolvedNode.asBoolean()) {
					flag = true;
					break;
				}
			}

			List<JsonNode> listJsonBeneficiary = objectMapper.readTree(json).findParents("beneficiaryFullName").stream()
					.filter(parentNode -> parentNode.get("senderFullName").textValue().equals(clientFullName)).toList();

			for (JsonNode nodo : listJsonBeneficiary) {
				JsonNode issueSolvedNode = nodo.get("issueSolved");
				if (issueSolvedNode != null && !issueSolvedNode.asBoolean()) {
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	/**
	 * Returns all transactions indexed by beneficiary name
	 */
	public Map<String, Object> getTransactionsByBeneficiaryName(String clientFullName, String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        List<JsonNode> transactions = StreamSupport.stream(jsonNode.spliterator(), false)
                .filter(transaction -> transaction.has("beneficiaryFullName")
                        && transaction.get("beneficiaryFullName").asText().equals(clientFullName))
                .collect(Collectors.toList());

        return Map.of(clientFullName, transactions);
	}

	/**
	 * Returns the identifiers of all open compliance issues
	 */
	public Set<Integer> getUnsolvedIssueIds() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a list of all solved issue messages
	 */
	public List<String> getAllSolvedIssueMessages() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the 3 transactions with highest amount sorted by amount descending
	 */
	public List<Object> getTop3TransactionsByAmount() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the sender with the most total sent amount
	 */
	public Optional<Object> getTopSender() {
		throw new UnsupportedOperationException();
	}

}
