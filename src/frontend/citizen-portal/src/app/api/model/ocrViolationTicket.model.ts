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
import { Field } from './field.model';


/**
 * A model representation of the extracted OCR results.
 */
export interface OcrViolationTicket { 
    /**
     * Gets or sets the saved image filename.
     */
    imageFilename?: string | null;
    /**
     * A global confidence of correctly extracting the document. This value will be low if the title of this   Violation Ticket form is not found (or of low confidence itself) or if the main ticket number is missing or invalid.
     */
    globalConfidence?: number;
    /**
     * An enumeration of all fields in this Violation Ticket.
     */
    fields?: { [key: string]: Field; } | null;
    ticket_number?: Field;
    surname?: Field;
    given_names?: Field;
    drivers_licence_number?: Field;
    drivers_licence_province?: Field;
    violation_date?: Field;
    violatio_time?: Field;
    is_mva_offence?: Field;
    is_mca_offence?: Field;
    is_cta_offence?: Field;
    is_wla_offence?: Field;
    is_faa_offence?: Field;
    is_lca_offence?: Field;
    is_tcr_offence?: Field;
    is_other_offence?: Field;
    provincial_court_hearing_location?: Field;
    organization_location?: Field;
    count1_description?: Field;
    count1_act_or_regulation?: Field;
    count1_section?: Field;
    count1_ticketed_amount?: Field;
    count1_is_act?: Field;
    count1_is_regulation?: Field;
    count2_description?: Field;
    count2_act_or_regulation?: Field;
    count2_section?: Field;
    count2_ticketed_amount?: Field;
    count2_is_act?: Field;
    count2_is_regulation?: Field;
    count3_description?: Field;
    count3_act_or_regulation?: Field;
    count3_section?: Field;
    count3_ticketed_amount?: Field;
    count3_is_act?: Field;
    count3_is_regulation?: Field;
}

