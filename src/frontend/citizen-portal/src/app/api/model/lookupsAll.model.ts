/**
 * Citizen API
 * Citizen API, for Citizen Portal frontend
 *
 * The version of the OpenAPI document: V0.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { Status } from './status.model';
import { Language } from './language.model';
import { Statute } from './statute.model';
import { CourtLocation } from './courtLocation.model';
import { Country } from './country.model';
import { PoliceLocation } from './policeLocation.model';
import { Province } from './province.model';


export interface LookupsAll { 
    courtLocations?: Array<CourtLocation>;
    languages?: Array<Language>;
    statuses?: Array<Status>;
    countries?: Array<Country>;
    provinces?: Array<Province>;
    statutes?: Array<Statute>;
    policeLocations?: Array<PoliceLocation>;
}
