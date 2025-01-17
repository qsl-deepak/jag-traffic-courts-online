package ca.bc.gov.open.jag.tco.oracledataapi.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ca.bc.gov.open.jag.tco.oracledataapi.model.Dispute;
import ca.bc.gov.open.jag.tco.oracledataapi.model.DisputeListItem;
import ca.bc.gov.open.jag.tco.oracledataapi.model.DisputeCount;
import ca.bc.gov.open.jag.tco.oracledataapi.model.Plea;
import ca.bc.gov.open.jag.tco.oracledataapi.model.ViolationTicketCount;
import ca.bc.gov.open.jag.tco.oracledataapi.model.YesNo;
import ca.bc.gov.open.jag.tco.oracledataapi.ords.occam.api.model.ViolationTicket;
import ca.bc.gov.open.jag.tco.oracledataapi.util.DateUtil;

/**
 * This mapper maps from ORDS dispute model to Oracle Data API dispute model
 */
@Mapper
(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR) // This is required for tests to work
public abstract class DisputeMapper extends BaseMapper {

	// Map dispute data from ORDS to Oracle Data API dispute model
	@Mapping(source = "dispute.entDtm", target = "createdTs")
	@Mapping(source = "dispute.entUserId", target = "createdBy")
	@Mapping(source = "dispute.updDtm", target = "modifiedTs")
	@Mapping(source = "dispute.updUserId", target = "modifiedBy")
	@Mapping(source = "dispute.disputeId", target = "disputeId")
	@Mapping(source = "dispute.disputeStatusTypeCd", target = "status", qualifiedByName="mapDisputeStatus")
	@Mapping(target = "disputeStatusType", ignore = true) // ignore back reference mapping
	@Mapping(source = "dispute.issuedDt", target = "issuedTs")
	@Mapping(source = "dispute.submittedDt", target = "submittedTs")
	@Mapping(source = "dispute.contactTypeCd", target = "contactTypeCd", qualifiedByName="mapContactTypeCd")
	@Mapping(source = "dispute.contactLawFirmNm", target = "contactLawFirmNm")
	@Mapping(source = "dispute.contactGiven1Nm", target = "contactGiven1Nm")
	@Mapping(source = "dispute.contactGiven2Nm", target = "contactGiven2Nm")
	@Mapping(source = "dispute.contactGiven3Nm", target = "contactGiven3Nm")
	@Mapping(source = "dispute.contactSurnameNm", target = "contactSurnameNm")
	@Mapping(source = "dispute.appearanceDtm", target = "appearanceDtm")
	@Mapping(source = "dispute.appearanceLessThan14Days", target = "appearanceLessThan14DaysYn")
	@Mapping(source = "dispute.disputantClientId", target = "disputantClientId")
	@Mapping(source = "dispute.disputantSurnameNm", target = "disputantSurname")
	@Mapping(source = "dispute.disputantGiven1Nm", target = "disputantGivenName1")
	@Mapping(source = "dispute.disputantGiven2Nm", target = "disputantGivenName2")
	@Mapping(source = "dispute.disputantGiven3Nm", target = "disputantGivenName3")
	@Mapping(source = "dispute.disputantBirthDt", target = "disputantBirthdate", dateFormat = DateUtil.DATE_FORMAT)
	@Mapping(source = "dispute.disputantOrganizationNm", target = "disputantOrganization")
	@Mapping(source = "dispute.disputantDrvLicNumberTxt", target = "driversLicenceNumber")
	@Mapping(source = "dispute.drvLicIssuedCtryId", target = "driversLicenceIssuedCountryId")
	@Mapping(source = "dispute.drvLicIssuedProvSeqNo", target = "driversLicenceIssuedProvinceSeqNo")
	@Mapping(source = "dispute.drvLicIssuedIntlProvTxt", target = "driversLicenceProvince")
	@Mapping(source = "dispute.requestCourtAppearanceYn", target = "requestCourtAppearanceYn")
	@Mapping(source = "dispute.addressLine1Txt", target = "addressLine1")
	@Mapping(source = "dispute.addressLine2Txt", target = "addressLine2")
	@Mapping(source = "dispute.addressLine3Txt", target = "addressLine3")
	@Mapping(source = "dispute.addressIntlCityTxt", target = "addressCity")
	@Mapping(source = "dispute.addressIntlProvTxt", target = "addressProvince")
	@Mapping(source = "dispute.addressProvCtryId", target = "addressProvinceCountryId")
	@Mapping(source = "dispute.addressProvSeqNo", target = "addressProvinceSeqNo")
	@Mapping(source = "dispute.addressCtryId", target = "addressCountryId")
	@Mapping(source = "dispute.postalCodeTxt", target = "postalCode")
	@Mapping(source = "dispute.homePhoneNumberTxt", target = "homePhoneNumber")
	@Mapping(source = "dispute.workPhoneNumberTxt", target = "workPhoneNumber")
	@Mapping(source = "dispute.emailAddressTxt", target = "emailAddress")
	@Mapping(source = "dispute.noticeOfDisputeGuid", target = "noticeOfDisputeGuid")
	@Mapping(source = "dispute.emailVerifiedYn", target = "emailAddressVerified", qualifiedByName="mapYnToBoolean")
	@Mapping(source = "dispute.filingDt", target = "filingDate")
	@Mapping(source = "dispute.representedByLawyerYn", target = "representedByLawyer")
	@Mapping(source = "dispute.lawFirmNm", target = "lawFirmName")
	@Mapping(target = "lawyerAddress", ignore = true)
	@Mapping(source = "dispute.lawyerSurnameNm", target = "lawyerSurname")
	@Mapping(source = "dispute.lawyerGiven1Nm", target = "lawyerGivenName1")
	@Mapping(source = "dispute.lawyerGiven2Nm", target = "lawyerGivenName2")
	@Mapping(source = "dispute.lawyerGiven3Nm", target = "lawyerGivenName3")
	@Mapping(source = "dispute.lawyerPhoneNumberTxt", target = "lawyerPhoneNumber")
	@Mapping(source = "dispute.lawyerEmailAddressTxt", target = "lawyerEmail")
	@Mapping(source = "dispute.officerPinTxt", target = "officerPin")
	@Mapping(source = "dispute.detachmentLocationTxt", target = "detachmentLocation")
	@Mapping(source = "dispute.courtAgenId", target="courtAgenId")
	@Mapping(source = "dispute.interpreterRequiredYn", target = "interpreterRequired")
	@Mapping(source = "dispute.languageCd", target = "interpreterLanguageCd")
	@Mapping(source = "dispute.witnessNo", target = "witnessNo")
	@Mapping(source = "dispute.fineReductionReasonTxt", target = "fineReductionReason")
	@Mapping(source = "dispute.timeToPayReasonTxt", target = "timeToPayReason")
	@Mapping(source = "dispute.disputantCommentTxt", target = "disputantComment")
	@Mapping(source = "dispute.rejectedReasonTxt", target = "rejectedReason")
	@Mapping(source = "dispute.userAssignedTo", target = "userAssignedTo")
	@Mapping(source = "dispute.userAssignedDtm", target = "userAssignedTs")
	@Mapping(source = "dispute.disputantDetectOcrIssuesYn", target = "disputantDetectedOcrIssues")
	@Mapping(source = "dispute.disputantOcrIssuesTxt", target = "disputantOcrIssues")
	@Mapping(source = "dispute.systemDetectOcrIssuesYn", target = "systemDetectedOcrIssues")
	@Mapping(source = "dispute.ocrTicketJsonFilenameTxt", target = "ocrTicketFilename")
	@Mapping(source = "dispute.signatoryTypeCd", target = "signatoryType")
	@Mapping(source = "dispute.signatoryNameTxt", target = "signatoryName")
	// Map violation ticket data from ORDS to Oracle Data API violation ticket model
	@Mapping(source = "entUserId", target = "violationTicket.createdBy")
	@Mapping(source = "entDtm", target = "violationTicket.createdTs")
	@Mapping(source = "updUserId", target = "violationTicket.modifiedBy")
	@Mapping(source = "updDtm", target = "violationTicket.modifiedTs")
	@Mapping(source = "violationTicketId", target = "violationTicket.violationTicketId")
	@Mapping(source = "ticketNumberTxt", target = "ticketNumber")
	@Mapping(source = "ticketNumberTxt", target = "violationTicket.ticketNumber")
	@Mapping(source = "disputantOrganizationNmTxt", target = "violationTicket.disputantOrganizationName")
	@Mapping(source = "disputantSurnameTxt", target = "violationTicket.disputantSurname")
	@Mapping(source = "disputantGivenNamesTxt", target = "violationTicket.disputantGivenNames")
	@Mapping(source = "isYoungPersonYn", target = "violationTicket.isYoungPerson")
	@Mapping(source = "disputantDrvLicNumberTxt", target = "violationTicket.disputantDriversLicenceNumber")
	@Mapping(source = "disputantClientNumberTxt", target = "violationTicket.disputantClientNumber")
	@Mapping(source = "drvLicIssuedProvinceTxt", target = "violationTicket.driversLicenceProvince")
	@Mapping(source = "drvLicIssuedCountryTxt", target = "violationTicket.driversLicenceCountry")
	@Mapping(source = "drvLicIssuedYearNo", target = "violationTicket.driversLicenceIssuedYear")
	@Mapping(source = "drvLicExpiryYearNo", target = "violationTicket.driversLicenceExpiryYear")
	@Mapping(source = "disputantBirthDt", target = "violationTicket.disputantBirthdate", dateFormat = DateUtil.DATE_FORMAT)
	@Mapping(source = "addressTxt", target = "violationTicket.address")
	@Mapping(source = "addressCityTxt", target = "violationTicket.addressCity")
	@Mapping(source = "addressProvinceTxt", target = "violationTicket.addressProvince")
	@Mapping(source = "addressPostalCodeTxt", target = "violationTicket.addressPostalCode")
	@Mapping(source = "addressCountryTxt", target = "violationTicket.addressCountry")
	@Mapping(source = "officerPinTxt", target = "violationTicket.officerPin")
	@Mapping(source = "detachmentLocationTxt", target = "violationTicket.detachmentLocation")
	@Mapping(source = "issuedDt", target = "violationTicket.issuedTs")
	@Mapping(source = "issuedOnRoadOrHighwayTxt", target = "violationTicket.issuedOnRoadOrHighway")
	@Mapping(source = "issuedAtOrNearCityTxt", target = "violationTicket.issuedAtOrNearCity")
	@Mapping(source = "isChangeOfAddressYn", target = "violationTicket.isChangeOfAddress")
	@Mapping(source = "isDriverYn", target = "violationTicket.isDriver")
	@Mapping(source = "isOwnerYn", target = "violationTicket.isOwner")
	@Mapping(source = "courtLocationTxt", target = "violationTicket.courtLocation")
	// Map violation ticket counts data from ORDS to Oracle Data API violation ticket count model
	@Mapping(source = "violationTicketCounts", target = "violationTicket.violationTicketCounts")
	// Map dispute counts data from ORDS to Oracle Data API dispute count model
	@Mapping(source = "violationTicketCounts", target = "disputeCounts", qualifiedByName="mapCounts")
	public abstract Dispute convertViolationTicketDtoToDispute (ViolationTicket violationTicketDto);

	// Map dispute data from ORDS to Oracle Data API dispute list item model
	@Mapping(source = "disputeId", target = "disputeId")
	@Mapping(source = "disputeStatusTypeCd", target = "status", qualifiedByName="mapDisputeStatus")
	@Mapping(source = "submittedDt", target = "submittedTs")
	@Mapping(source = "disputantSurnameNm", target = "disputantSurname")
	@Mapping(source = "ticketNumberTxt", target = "ticketNumber")
	@Mapping(source = "disputantGiven1Nm", target = "disputantGivenName1")
	@Mapping(source = "disputantGiven2Nm", target = "disputantGivenName2")
	@Mapping(source = "disputantGiven3Nm", target = "disputantGivenName3")
	@Mapping(source = "requestCourtAppearanceYn", target = "requestCourtAppearanceYn")
	@Mapping(source = "emailAddressTxt", target = "emailAddress")
	@Mapping(source = "emailVerifiedYn", target = "emailAddressVerified", qualifiedByName="mapYnToBoolean")
	@Mapping(source = "filingDt", target = "filingDate")
	@Mapping(source = "userAssignedTo", target = "userAssignedTo")
	@Mapping(source = "userAssignedDtm", target = "userAssignedTs")
	@Mapping(source = "disputantDetectOcrIssuesYn", target = "disputantDetectedOcrIssues")
	@Mapping(source = "systemDetectOcrIssuesYn", target = "systemDetectedOcrIssues")
	@Mapping(source = "violationDt", target = "violationDate")
	@Mapping(source = "tcoDisputeStatus", target = "jjDisputeStatus", qualifiedByName="mapJJDisputeStatus")
	@Mapping(source = "jjAssignedTo", target = "jjAssignedTo")
	@Mapping(source = "mostRecentDecisionMadeBy", target = "decisionMadeBy")
	@Mapping(source = "jjDecisionDt", target = "jjDecisionDate")
	@Mapping(source = "courtAgenId", target = "courtAgenId")
	public abstract DisputeListItem convertDisputeListItemDtoToDisputeListItem (ca.bc.gov.open.jag.tco.oracledataapi.ords.occam.api.model.DisputeListItem disputeListItemDto);

	@Mapping(target = "violationTicket", ignore = true) // ignore back reference mapping
	@Mapping(source = "entUserId", target = "createdBy")
	@Mapping(source = "entDtm", target = "createdTs")
	@Mapping(source = "updUserId", target = "modifiedBy")
	@Mapping(source = "updDtm", target = "modifiedTs")
	@Mapping(source = "descriptionTxt", target = "description")
	@Mapping(source = "actOrRegulationNameCd", target = "actOrRegulationNameCode")
	@Mapping(source = "isActYn", target = "isAct")
	@Mapping(source = "isRegulationYn", target = "isRegulation")
	@Mapping(source = "statSectionTxt", target = "section")
	@Mapping(source = "statSubSectionTxt", target = "subsection")
	@Mapping(source = "statParagraphTxt", target = "paragraph")
	@Mapping(source = "statSubParagraphTxt", target = "subparagraph")
	@Mapping(source = "ticketedAmt", target = "ticketedAmount")
	public abstract ViolationTicketCount convertViolationTicketCountDtoToViolationTicketCount (ca.bc.gov.open.jag.tco.oracledataapi.ords.occam.api.model.ViolationTicketCount violationTicketCountDto);

	/**
	 * Custom mapping for dispute counts
	 *
	 * @param violationTicketCounts
	 * @return list of {@link DisputeCount}
	 */
	@Named("mapCounts")
	protected List<DisputeCount> mapCounts(List<ca.bc.gov.open.jag.tco.oracledataapi.ords.occam.api.model.ViolationTicketCount> violationTicketCounts) {
		if ( violationTicketCounts == null || violationTicketCounts.isEmpty()) {
			return null;
		}

		List<DisputeCount> disputeCounts = new ArrayList<DisputeCount>();

		for (ca.bc.gov.open.jag.tco.oracledataapi.ords.occam.api.model.ViolationTicketCount violationTicketCount : violationTicketCounts) {
			ca.bc.gov.open.jag.tco.oracledataapi.ords.occam.api.model.DisputeCount disputeCount = violationTicketCount.getDisputeCount();
			if (disputeCount != null) {
				DisputeCount count = new DisputeCount();
				count.setCreatedBy(disputeCount.getEntUserId());
				count.setCreatedTs(disputeCount.getEntDtm());
				count.setModifiedBy(disputeCount.getUpdUserId());
				count.setModifiedTs(disputeCount.getUpdDtm());
				if (!StringUtils.isBlank(violationTicketCount.getCountNo())) {
					count.setCountNo(Integer.parseInt(violationTicketCount.getCountNo()));
				}
				if (!StringUtils.isBlank(violationTicketCount.getViolationTicketCountId())) {
					count.setDisputeCountId(Long.getLong(violationTicketCount.getViolationTicketCountId()));
				}
				if (disputeCount.getPleaCd() != null) {
					count.setPleaCode(Enum.valueOf(Plea.class, disputeCount.getPleaCd()));
				}
				if (disputeCount.getRequestCourtAppearanceYn() != null) {
					count.setRequestCourtAppearance(Enum.valueOf(YesNo.class, disputeCount.getRequestCourtAppearanceYn()));
				}
				if (disputeCount.getRequestReductionYn() != null) {
					count.setRequestReduction(Enum.valueOf(YesNo.class, disputeCount.getRequestReductionYn()));
				}
				if (disputeCount.getRequestTimeToPayYn() != null) {
					count.setRequestTimeToPay(Enum.valueOf(YesNo.class, disputeCount.getRequestTimeToPayYn()));
				}
				disputeCounts.add(count);
			}
		}
		return disputeCounts;
	}

	@AfterMapping
	protected void setLawyerAddress(@MappingTarget Dispute dispute, ViolationTicket violationTicket) {
		ca.bc.gov.open.jag.tco.oracledataapi.ords.occam.api.model.Dispute disputeFromApi = violationTicket.getDispute();
		if (dispute != null && violationTicket != null && disputeFromApi != null) {
			String addressLine1 = disputeFromApi.getLawFirmAddrLine1Txt();
			String addressLine2 = disputeFromApi.getLawFirmAddrLine2Txt();
			String addressLine3 = disputeFromApi.getLawFirmAddrLine3Txt();
			if (addressLine1 == null && addressLine2 == null && addressLine3 == null) {
				dispute.setLawyerAddress(null);
			} else {
				String fullLawyerAddress = "";
				fullLawyerAddress += addressLine1 == null ? "" : addressLine1;
				fullLawyerAddress += addressLine2 == null ? "" : addressLine2;
				fullLawyerAddress += addressLine3 == null ? "" : addressLine3;
				dispute.setLawyerAddress(fullLawyerAddress);
			}
		}
	}
}
