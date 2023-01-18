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


/**
 * A subset of a Disputant\'s contact information that can be requested to update via a PUT /api/dispute/{guidhash}/contact endpoint.
 */
export interface DisputantContactInformation { 
    /**
     * The disputant\'s email address.
     */
    email_address?: string | null;
    /**
     * The first given name or corporate name continued.
     */
    disputant_given_name1?: string | null;
    /**
     * The second given name
     */
    disputant_given_name2?: string | null;
    /**
     * The third given name
     */
    disputant_given_name3?: string | null;
    /**
     * The surname or corporate name.
     */
    disputant_surname?: string | null;
    /**
     * The mailing address of the disputant.
     */
    address_line1?: string | null;
    /**
     * The mailing address of the disputant.
     */
    address_line2?: string | null;
    /**
     * The mailing address of the disputant.
     */
    address_line3?: string | null;
    /**
     * The mailing address city of the disputant.
     */
    address_city?: string | null;
    /**
     * The mailing address province of the disputant.
     */
    address_province?: string | null;
    /**
     * The mailing address province\'s country code of the disputant.
     */
    address_province_country_id?: number | null;
    /**
     * The mailing address province\'s sequence number of the disputant.
     */
    address_province_seq_no?: number | null;
    /**
     * The mailing address country id of the disputant.
     */
    address_country_id?: number | null;
    /**
     * The mailing address postal code or zip code of the disputant.
     */
    postal_code?: string | null;
    /**
     * The disputant\'s home phone number.
     */
    home_phone_number?: string | null;
}
