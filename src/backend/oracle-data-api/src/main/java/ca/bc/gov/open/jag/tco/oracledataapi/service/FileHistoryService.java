package ca.bc.gov.open.jag.tco.oracledataapi.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.open.jag.tco.oracledataapi.model.FileHistory;
import ca.bc.gov.open.jag.tco.oracledataapi.repository.FileHistoryRepository;

@Service
public class FileHistoryService {

	@Autowired
	FileHistoryRepository fileHistoryRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	/**
	 * Retrieves {@link FileHistory} records by Ticket Number, delegating to CrudRepository
	 * @param ticketNumber the id for which to retrieve file history records
	 * @return
	 */
	public List<FileHistory> getFileHistoryByTicketNumber(String ticketNumber) {
		return fileHistoryRepository.findByTicketNumber(ticketNumber);
	}
	
	/**
	 * Inserts an email history record
	 *
	 * @param ticketNumber
	 * @param {@link FileHistory}
	 * @return
	 */
	@Transactional
	public Long insertFileHistory(String ticketNumber, FileHistory fileHistory) {
		
		fileHistory.setFileHistoryId(null);
		fileHistory.setTicketNumber(ticketNumber);
		fileHistoryRepository.saveAndFlush(fileHistory);
		return fileHistory.getFileHistoryId();
	}
}