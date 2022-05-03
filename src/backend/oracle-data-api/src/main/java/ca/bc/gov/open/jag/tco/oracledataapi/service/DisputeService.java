package ca.bc.gov.open.jag.tco.oracledataapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.bc.gov.open.jag.tco.oracledataapi.error.NotAllowedException;
import ca.bc.gov.open.jag.tco.oracledataapi.model.Dispute;
import ca.bc.gov.open.jag.tco.oracledataapi.model.DisputeStatus;
import ca.bc.gov.open.jag.tco.oracledataapi.model.TicketCount;
import ca.bc.gov.open.jag.tco.oracledataapi.repository.DisputeRepository;

@Service
public class DisputeService {

	private Logger logger = LoggerFactory.getLogger(DisputeService.class);

	@Autowired
	DisputeRepository disputeRepository;

	/**
	 * Retrieves all {@link Dispute} records, delegating to CrudRepository
	 *
	 * @return
	 */
	public List<Dispute> getAllDisputes() {
		List<Dispute> disputes = new ArrayList<Dispute>();
		disputeRepository.findAll().forEach(dispute -> disputes.add(dispute));
		return disputes;
	}

	/**
	 * Retrieves a specific {@link Dispute} by using the method findById() of CrudRepository
	 *
	 * @param id of the Dispute to be returned
	 * @return
	 */
	public Dispute getDisputeById(UUID id) {
		return disputeRepository.findById(id).orElseThrow();
	}

	/**
	 * Create a new {@link Dispute} by using the method save() of CrudRepository
	 *
	 * @param dispute to be saved
	 */
	public void save(Dispute dispute) {
		// Ensure a new record is created, not updating an existing record. Updates are controlled by specific endpoints.
		dispute.setId(null);
		for (TicketCount ticketCount : dispute.getTicketCounts()) {
			ticketCount.setId(null);
		}
		disputeRepository.save(dispute);
	}

	/**
	 * Updates the properties of a specific {@link Dispute}
	 *
	 * @param id
	 * @param {@link Dispute}
	 * @return
	 */
	public Dispute update(UUID id, Dispute dispute) {
		Dispute disputeToUpdate = disputeRepository.findById(id).orElseThrow();

		BeanUtils.copyProperties(dispute, disputeToUpdate, "id", "ticketCounts");
		// Remove all existing ticket counts that are associated to this dispute
		if (disputeToUpdate.getTicketCounts() != null) {
			disputeToUpdate.getTicketCounts().clear();
		}
		// Add updated ticket counts
		disputeToUpdate.addTicketCounts(dispute.getTicketCounts());

		return disputeRepository.save(disputeToUpdate);
	}

	/**
	 * Deletes a specific {@link Dispute} by using the method deleteById() of CrudRepository
	 *
	 * @param id of the dispute to be deleted
	 */
	public void delete(UUID id) {
		disputeRepository.deleteById(id);
	}

	/**
	 * Updates the status of a specific {@link Dispute}
	 *
	 * @param id
	 * @param disputeStatus
	 * @return the saved Dispute
	 */
	public Dispute setStatus(UUID id, DisputeStatus disputeStatus) {
		return setStatus(id, disputeStatus, null);
	}

	/**
	 * Updates the status of a specific {@link Dispute}
	 *
	 * @param id
	 * @param disputeStatus
	 * @param rejectedReason the rejected reason if the status is REJECTED.
	 * @return the saved Dispute
	 */
	public Dispute setStatus(UUID id, DisputeStatus disputeStatus, String rejectedReason) {
		if (disputeStatus == null) {
			logger.error("Attempting to set Dispute status to null - bad method call.");
			throw new NotAllowedException("Cannot set Dispute status to null");
		}

		Dispute dispute = disputeRepository.findById(id).orElseThrow();

		// TCVP-1058 - business rules
		// - current status must be NEW,REJECTED to change to PROCESSING
		// - current status must be NEW to change to REJECTED
		// - current status must be NEW,PROCESSING,REJECTED to change to CANCELLED
		switch (disputeStatus) {
		case PROCESSING:
			if (!List.of(DisputeStatus.NEW, DisputeStatus.REJECTED).contains(dispute.getStatus())) {
				throw new NotAllowedException("Changing the status of a Dispute record from %s to %s is not permitted.", dispute.getStatus(), DisputeStatus.PROCESSING);
			}
			break;
		case CANCELLED:
			if (!List.of(DisputeStatus.NEW, DisputeStatus.PROCESSING, DisputeStatus.REJECTED).contains(dispute.getStatus())) {
				throw new NotAllowedException("Changing the status of a Dispute record from %s to %s is not permitted.", dispute.getStatus(), DisputeStatus.CANCELLED);
			}
			break;
		case REJECTED:
			if (!List.of(DisputeStatus.NEW).contains(dispute.getStatus())) {
				throw new NotAllowedException("Changing the status of a Dispute record from %s to %s is not permitted.", dispute.getStatus(), DisputeStatus.REJECTED);
			}
			break;
		case NEW:
			// This should never happen since setting the status to NEW should only happen during initial creation of the Dispute record.
			// If we got here, then this means the Dispute record is in an invalid state.
			logger.error("Attempting to set the status of a Dispute record to NEW after it was created - bad object state.");
			throw new NotAllowedException("Changing the status of a Dispute record to %s is not permitted.", DisputeStatus.NEW);
		default:
			// This should never happen, but if so, then it means a new DisputeStatus was added and these business rules were not updated accordingly.
			logger.error("A Dispute record has an unknown status '{}' - bad object state.", dispute.getStatus());
			throw new NotAllowedException("Unknown status of a Dispute record: %s", dispute.getStatus());
		}

		dispute.setStatus(disputeStatus);
		dispute.setRejectedReason(DisputeStatus.REJECTED.equals(disputeStatus) ? rejectedReason : null);
		return disputeRepository.save(dispute);
	}

}