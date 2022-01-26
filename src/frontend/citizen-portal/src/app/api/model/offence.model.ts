/**
 * Traffic Court Online Citizen Api
 * An API for creating violation ticket disputes
 *
 * The version of the OpenAPI document: v1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { DisputeStatus } from './disputeStatus.model';


export interface Offence { 
    offenceNumber?: number;
    ticketedAmount?: number;
    amountDue?: number;
    violationDateTime?: string | null;
    offenceDescription?: string | null;
    vehicleDescription?: string | null;
    discountAmount?: number;
    discountDueDate?: string | null;
    invoiceType?: string | null;
    offenceAgreementStatus?: string | null;
    requestReduction?: boolean;
    requestMoreTime?: boolean;
    reductionAppearInCourt?: boolean | null;
    reductionReason?: string | null;
    moreTimeReason?: string | null;
    status?: DisputeStatus;
}

