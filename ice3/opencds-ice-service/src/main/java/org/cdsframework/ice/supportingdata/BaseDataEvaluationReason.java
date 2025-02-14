/**
 * Copyright (C) 2022 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see <http://www.gnu.org/licenses/> for more
 * details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the New York City
 * Department of Health and Mental Hygiene, Bureau of Immunization to have (without restriction,
 * limitation, and warranty) complete irrevocable access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; THE
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see http://www.hln.com/ice or send
 * correspondence to ice@hln.com.
 */

package org.cdsframework.ice.supportingdata;

public enum BaseDataEvaluationReason implements BaseData {

	_ABOVE_REC_AGE_SERIES("EVALUATION_REASON_CONCEPT.ABOVE_REC_AGE_SERIES"),
	_ABOVE_MAXIMUM_AGE_VACCINE("EVALUATION_REASON_CONCEPT.ABOVE_MAXIMUM_AGE_VACCINE"),
	_BELOW_MINIMUM_AGE_EVALUATION_REASON("EVALUATION_REASON_CONCEPT.BELOW_MINIMUM_AGE_SERIES"),
	_BELOW_MINIMUM_INTERVAL_EVALUATION_REASON("EVALUATION_REASON_CONCEPT.BELOW_MINIMUM_INTERVAL"),
	_BELOW_MINIMUM_AGE_FINAL_DOSE_REASON("EVALUATION_REASON_CONCEPT.BELOW_MINIMUM_AGE_FINAL_DOSE"),
	_BELOW_MINIMUM_AGE_VACCINE_REASON("EVALUATION_REASON_CONCEPT.BELOW_MINIMUM_AGE_VACCINE"),
	_BELOW_MINIMUM_INTERVAL_PCV_PPSV("EVALUATION_REASON_CONCEPT.BELOW_MIN_INTERVAL_PCV_PPSV"),
	_BELOW_REC_AGE_SERIES("EVALUATION_REASON_CONCEPT.BELOW_REC_AGE_SERIES"),
	_DOSE_OVERRIDE_INVALID("EVALUATION_REASON_CONCEPT.DOSE_OVERRIDE_INVALID"),
	_DOSE_OVERRIDE_VALID("EVALUATION_REASON_CONCEPT.DOSE_OVERRIDE_VALID"),
	_DUPLICATE_SAME_DAY_REASON("EVALUATION_REASON_CONCEPT.DUPLICATE_SAME_DAY"),
	_EXTRA_DOSE_EVALUATION_REASON("EVALUATION_REASON_CONCEPT.EXTRA_DOSE"),
	_INSUFFICIENT_ANTIGEN_REASON("EVALUATION_REASON_CONCEPT.INSUFFICIENT_ANTIGEN"),
	_INVALID_AGE_EVALUATION_REASON("EVALUATION_REASON_CONCEPT.INVALID_AGE"),
	_OUTSIDE_SEASON_REASON("EVALUATION_REASON_CONCEPT.OUTSIDE_SEASON"),
	_OUTSIDE_FLU_SEASON_REASON("EVALUATION_REASON_CONCEPT.OUTSIDE_FLU_VAC_SEASON"),
	_PRIOR_TO_DOB("EVALUATION_REASON_CONCEPT.PRIOR_TO_DOB"),
	_SELECT_ADJUVANT_PRODUCT_INTERVAL("EVALUATION_REASON_CONCEPT.SELECT_ADJUVANT_PRODUCT_INTERVAL"),
	_SUPPLEMENTAL_TEXT("EVALUATION_REASON_CONCEPT.SUPPLEMENTAL_TEXT"),
	_TOO_EARLY_LIVE_VIRUS("EVALUATION_REASON_CONCEPT.TOO_EARLY_LIVE_VIRUS"),
	_VACCINE_NOT_ALLOWED("EVALUATION_REASON_CONCEPT.VACCINE_NOT_ALLOWED"),
	_VACCINE_NOT_ALLOWED_FOR_THIS_DOSE("EVALUATION_REASON_CONCEPT.VACCINE_NOT_ALLOWED_FOR_THIS_DOSE"),
	_VACCINE_NOT_PART_OF_THIS_SERIES("EVALUATION_REASON_CONCEPT.VACCINE_NOT_PART_OF_THIS_SERIES"),
	_VACCINE_NOT_SUPPORTED_REASON("EVALUATION_REASON_CONCEPT.VACCINE_NOT_SUPPORTED"),
	_UNSPECIFIED_REASON("EVALUATION_REASON_CONCEPT.UNSPECIFIED_REASON");
	
	private String cdsListItemName;
	
	private BaseDataEvaluationReason() {
		this.cdsListItemName = null;
	}
	
	private BaseDataEvaluationReason(String pEvaluationReasonCdsListItem) {
		this.cdsListItemName = pEvaluationReasonCdsListItem;
	}
	
	public String getCdsListItemName() {
		return this.cdsListItemName;
	}
	
}
