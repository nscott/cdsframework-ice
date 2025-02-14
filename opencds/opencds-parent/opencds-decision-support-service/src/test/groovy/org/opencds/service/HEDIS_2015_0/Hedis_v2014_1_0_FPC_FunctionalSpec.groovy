package org.opencds.service.HEDIS_2015_0;

import org.opencds.service.util.OpencdsClient
import org.opencds.service.util.VMRUtil

import spock.lang.Specification
import spock.lang.Unroll

public class Hedis_v2014_1_0_FPC_FunctionalSpec extends Specification {

	/*
	 * Assumptions: Measurement Year is 2011, encounter low date is proxy for delivery date when it is missing from procedure
	 * old outputs, now obsolete
	 * C3298 = FPC First Delivery
	 * C3304 - 0 - 20% expected visits - 3387
	 * C3305 - 21 - 40% -
	 * C3306 - 41 - 60%
	 * C3307 - 61 - 80%
	 * C3308 - 81 - 100%
	 *
	 * new outputs, any one of the following per loop:
	 * C3386 - HEDIS FPC - 0 - 20%
	 * C3387 - HEDIS FPC - 21 - 40%
	 * C3388 - HEDIS FPC - 41 - 60%
	 * C3389 - HEDIS FPC - 61 - 80%
	 * C3390 - HEDIS FPC - 81 - 100%
	 * C3341 - HEDIS FPC  (e.g., not met denominator, bad data, etc.)
	 */

        	/* 0 : Denom check: Missing DOB value, this is NOT invalid data for this measure 	*/
			/* 0 : Num check: nothing  */
	private static final String VMR_000 = "src/test/resources/samples/hedis-fpc/FPC_000.xml"
	private static final Map   MEAS_000 = [C3341: [num: 0, denom: 0]] 
	private static final Map  ASSRT_000 = ['denomNotMet': '']      
		
			/* 0 : Denom check: Gender Male value, this is NOT invalid data for this measure 	*/
			/* 0 : Num check: nothing  */
	private static final String VMR_001 = "src/test/resources/samples/hedis-fpc/FPC_001.xml"
	private static final Map   MEAS_001 = [C3341: [num: 0, denom: 0]] 
	private static final Map  ASSRT_001 = ['denomNotMet': ''] 
		
        	/* 0 : Denom check: Missing Gender value, this is NOT invalid data for this measure 	*/
			/* 0 : Num check: nothing  */
	private static final String VMR_002 = "src/test/resources/samples/hedis-fpc/FPC_002.xml"
	private static final Map   MEAS_002 = [C3341: [num: 0, denom: 0]] 
	private static final Map  ASSRT_002 = ['denomNotMet': '']  	
		
        	/* 0 : Denom check: Missing Gender value and DOB, this is NOT invalid data for this measure 	*/
			/* 0 : Num check: nothing  */
	private static final String VMR_003 = "src/test/resources/samples/hedis-fpc/FPC_003.xml"
	private static final Map   MEAS_003 = [C3341: [num: 0, denom: 0]] 
	private static final Map  ASSRT_003 = ['denomNotMet': '']  	
		
        	/* 1 - Denom check: HEDIS Delivery by ICD9Dx just before high cutoff */
			/* 0 - Num check: nothing  */
	private static final String VMR_004 = "src/test/resources/samples/hedis-fpc/FPC_004.xml"
	private static final Map   MEAS_004 = ['C3386': [num: 1, denom: 1]] 
	private static final Map  ASSRT_004 = ['denomMet(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']	
		
        	/* 0 - Denom check: HEDIS Delivery by ICD9Dx, just before low cutoff	*/
			/* 0 - Num check: nothing  */
	private static final String VMR_005 = "src/test/resources/samples/hedis-fpc/FPC_005.xml"
	private static final Map   MEAS_005 = ['C3341': [num: 0, denom: 0]] 
	private static final Map  ASSRT_005 = ['denomNotMet(0)': ''] 	
		
        	/* 0 - Denom check: HEDIS Delivery by ICD9Dx, just after high cutoff	*/
			/* 0 - Num check: nothing  */
	private static final String VMR_006 = "src/test/resources/samples/hedis-fpc/FPC_006.xml"
	private static final Map   MEAS_006 = ['C3341': [num: 0, denom: 0]] 
	private static final Map  ASSRT_006 = ['denomNotMet': ''] 	
		
        	/* 1 - Denom check: HEDIS Delivery by ICD9Dx, just after low cutoff	*/
			/* 0 - Num check: nothing  */
	private static final String VMR_007 = "src/test/resources/samples/hedis-fpc/FPC_007.xml"
	private static final Map   MEAS_007 = ['C3386': [num: 1, denom: 1]] 
	private static final Map  ASSRT_007 = ['denomMet(0)': '', 'PercentLT21(0)': '', 'PrenatalVisitDistinctDateCount(0)': '0'] 	
		
			/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
			/* 0 - Num check: nothing  */
	private static final String VMR_008 = "src/test/resources/samples/hedis-fpc/FPC_008.xml"
	private static final Map   MEAS_008 = ['C3386': [num: 1, denom: 1]] 
	private static final Map  ASSRT_008 = ['denomMet(0)': '', 'PercentLT21(0)': '', 'PrenatalVisitDistinctDateCount(0)': '0']	
		
        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, just after low cutoff, w/gestational age = 8	*/
			/* 0 - Num check: nothing  */
	private static final Map   MEAS_009 = ['C3386': [num: 1, denom: 1]] 
	private static final Map  ASSRT_009 = ['denomMet(0)': '', 'PercentLT21(0)': '', 'PrenatalVisitDistinctDateCount(0)': '0']	
		
        	/* 0 - Denom check: HEDIS Delivery by ICD9Px, not live birth	*/
			/* 0 - Num check: nothing  */
	private static final Map   MEAS_010 = ['C3341': [num: 0, denom: 0]]
	private static final Map  ASSRT_010 = ['denomNotMet': '']	
		
        	/* 1 - Denom check: HEDIS Delivery by ICD9Px	*/
			/* 1 - Num check: one standalone prenatal visit by CPT, w/provider, 1 month before delivery  */
	private static final Map   MEAS_011 = ['C3386': [num: 1, denom: 1]] 
	private static final Map  ASSRT_011 = ['denomMet(0)': '', 'PercentLT21(0)': '', 'PrenatalVisitDistinctDateCount(0)': '1']	
		
        	/* 1 - Denom check: HEDIS Delivery by ICD9Px	*/
			/* 0 - Num check: one standalone prenatal visit by CPT, w/o provider, 1 month before delivery  */
	private static final Map   MEAS_012 = ['C3386': [num: 1, denom: 1]] 
	private static final Map  ASSRT_012 = ['denomMet(0)': '', 'PercentLT21(0)': '', 'PrenatalVisitDistinctDateCount(0)': '0']	
		
        	/* 1 - Denom check: HEDIS Delivery by ICD9Px	*/
			/* 1 - Num check: one standalone prenatal visit by HCPCS, w/ provider, 1 month before delivery  */
	private static final Map   MEAS_013 = ['C3386': [num: 1, denom: 1]]
	private static final Map  ASSRT_013 = ['denomMet(0)': '', 'PercentLT21(0)': '', 'PrenatalVisitDistinctDateCount(0)': '1']	
		
        	/* 1 - Denom check: HEDIS Delivery by ICD9Px	*/
			/* 0 - Num check: one standalone prenatal visit by HCPCS, w/o provider, 1 month before delivery  */
	private static final Map   MEAS_014 = ['C3386': [num: 1, denom: 1]]
	private static final Map  ASSRT_014 = ['denomMet(0)': '', 'PercentLT21(0)': '', 'PrenatalVisitDistinctDateCount(0)': '0']
		
        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
			/* 1 - Num check: full set standalone prenatal visit by HCPCS, w/ provider, count of 17 visits before delivery  */
	private static final Map   MEAS_015 = ['C3390': [num: 1, denom: 1]]
	private static final Map  ASSRT_015 = ['denomMet(0)': '', 'PercentGE81(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '17']	
		
        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43, 2nd delivery by ICD9Px, gestational age 38	*/
			/* 1 - Num check: full set standalone prenatal visit by HCPCS, w/ provider, count of 17 visits before delivery  */
	private static final Map   MEAS_016 = ['C3390': [num: 1, denom: 1]]
	private static final Map  ASSRT_016 = ['denomMet(0)': '', 'PercentGE81(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '17']	
		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: OB Panel 1st trimester by CPT  */
//	private static final Map   MEAS_017 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_017 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: Ultrasound 1st trimester by CPT */
//	private static final Map   MEAS_018 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_018 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: Pregnancy Dx 1st trimester  */
//	private static final Map   MEAS_019 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_019 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: OB Panel 1st trimester by ICD9Px  */
//	private static final Map   MEAS_020 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_020 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: antibody tests 1st trimester by CPT  */
//	private static final Map   MEAS_021 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_021 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 0 - Num check: antibody tests 1st trimester by LOINC  */
//	private static final Map   MEAS_022 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_022 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 0 - Num check: antibody tests 1st trimester by LOINC, minus Toxoplasma  */
//	private static final Map   MEAS_023 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_023 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 0 - Num check: antibody tests 1st trimester by LOINC, minus Rubella  */
//	private static final Map   MEAS_024 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_024 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 0 - Num check: antibody tests 1st trimester by LOINC, minus cytomegalovirus  */
//	private static final Map   MEAS_025 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_025 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 0 - Num check: antibody tests 1st trimester by LOINC, minus herpes simplex  */
//	private static final Map   MEAS_026 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_026 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: rubella and ABO in 1st trimester by LOINC */
//	private static final Map   MEAS_027 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_027 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: rubella and ABO in 1st trimester by CPT */
//	private static final Map   MEAS_028 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_028 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 0 - Num check: rubella and ABO in 1st trimester by CPT, minus rubella */
//		private static final Map MEAS_029 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_029 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 0 - Num check: rubella and ABO in 1st trimester by CPT, minus ABO */
//		private static final Map MEAS_030 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_030 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: rubella and Rh in 1st trimester by CPT */
//		private static final Map MEAS_031 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_031 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//			
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: rubella and Rh in 1st trimester by LOINC */
//		private static final Map MEAS_032 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_032 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: rubella and Rh in 1st trimester by LOINC, minus rubella */
//		private static final Map MEAS_033 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_033 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: rubella by CPT and ABO_RH by LOINC in 1st trimester */
//		private static final Map MEAS_034 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_034 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 1 - Num check: rubella by LOINC and ABO_RH by LOINC in 1st trimester */
//		private static final Map MEAS_035 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_035 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '1']
//			
//        	/* 1 - Denom check: HEDIS Delivery by CPT, just after low cutoff	*/
//			/* 0 - Num check: ABO_RH by LOINC in 1st trimester */
//		private static final Map MEAS_036 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_036 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT, encounter before, procedure after low cutoff	*/
//			/* 0 - Num check: nothing  */
//		private static final Map MEAS_037 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_037 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']	
//		
//        	/* 0 - Denom check: HEDIS Delivery by CPT, encounter before, procedure after high cutoff	*/
//			/* 0 - Num check: nothing  */
//		private static final Map MEAS_038 = [C3341: [num: 0, denom: 0]]
//	private static final Map  ASSRT_038 = ['denomNotMet(0)': '']
//		
//        	/* 1 - Denom check: HEDIS Delivery by CPT	*/
//			/* 0 - Num check: nothing  */
//		private static final Map MEAS_039 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_039 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '14', 'PrenatalVisitDistinctDateCount(0)': '0']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check:  set standalone prenatal visit by HCPCS, w/ provider, count of 16 visits before delivery  */
//		private static final Map MEAS_040 = [C3390: [num: 1, denom: 1]]
//	private static final Map  ASSRT_040 = [C3308: '', 'PercentGE81(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '16']	
//			
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check:  set standalone prenatal visit by HCPCS, w/ provider, count of 15 visits before delivery  */
//		private static final Map MEAS_041 = [C3390: [num: 1, denom: 1]]
//	private static final Map  ASSRT_041 = [C3308: '', 'PercentGE81(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '15']	
//				
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 14 visits before delivery  */
//		private static final Map MEAS_042 = [C3390: [num: 1, denom: 1]]
//	private static final Map  ASSRT_042 = [C3308: '', 'PercentGE81(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '14']	
//		  
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 13 visits before delivery  */
//		private static final Map MEAS_043 = [C3389: [num: 1, denom: 1]]
//	private static final Map  ASSRT_043 = [C3307: '', 'PercentGE61LE80(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '13']	 
//		 
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 12 visits before delivery  */
//		private static final Map MEAS_044 = [C3389: [num: 1, denom: 1]]
//	private static final Map  ASSRT_044 = [C3307: '', 'PercentGE61LE80(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '12']	 
//		 
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 11 visits before delivery  */
//		private static final Map MEAS_045 = [C3389: [num: 1, denom: 1]]
//	private static final Map  ASSRT_045 = [C3307: '', 'PercentGE61LE80(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '11']	
//			  
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 10 visits before delivery  */
//		private static final Map MEAS_046 = [C3388: [num: 1, denom: 1]]
//	private static final Map  ASSRT_046 = [C3306: '', 'PercentGE41LE60(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '10']	  
//		
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 9 visits before delivery  */
//		private static final Map MEAS_047 = [C3388: [num: 1, denom: 1]]
//	private static final Map  ASSRT_047 = [C3306: '', 'PercentGE41LE60(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '9']
//		 
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 8 visits before delivery  */
//		private static final Map MEAS_048 = [C3388: [num: 1, denom: 1]]
//	private static final Map  ASSRT_048 = [C3306: '', 'PercentGE41LE60(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '8']	
//		
//			/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 7 visits before delivery  */
//		private static final Map MEAS_049 = [C3388: [num: 1, denom: 1]]
//	private static final Map  ASSRT_049 = [C3306: '', 'PercentGE41LE60(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '7']
//			   
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 6 visits before delivery  */
//		private static final Map MEAS_050 = [C3387: [num: 1, denom: 1]]
//	private static final Map  ASSRT_050 = [C3305: '', 'PercentGE21LE40(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '6']	
//			
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 5 visits before delivery  */
//		private static final Map MEAS_051 = [C3387: [num: 1, denom: 1]]
//	private static final Map  ASSRT_051 = [C3305: '', 'PercentGE21LE40(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '5']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 4 visits before delivery  */
//		private static final Map MEAS_052 = [C3387: [num: 1, denom: 1]]
//	private static final Map  ASSRT_052 = [C3305: '', 'PercentGE21LE40(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '4']
//		
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 3 visits before delivery  */
//		private static final Map MEAS_053 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_053 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '3']	
//		
//			/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 2 visits before delivery  */
//		private static final Map MEAS_054 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_054 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '2']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 1 visits before delivery  */
//		private static final Map MEAS_055 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_055 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '1']	
//		
//        	/* 1 - Denom check: HEDIS Delivery by ICD9Px, gestation age 43	*/
//			/* 1 - Num check: set standalone prenatal visit by HCPCS, w/ provider, count of 0 visits before delivery  */
//		private static final Map MEAS_056 = [C3386: [num: 1, denom: 1]]
//	private static final Map  ASSRT_056 = [C3304: '', 'PercentLT21(0)': '', 'ExpectedVisits(0)': '17', 'PrenatalVisitDistinctDateCount(0)': '0']	
		
	private static final String VMR_009 = "src/test/resources/samples/hedis-fpc/FPC_009.xml"
	private static final String VMR_010 = "src/test/resources/samples/hedis-fpc/FPC_010.xml"
	private static final String VMR_011 = "src/test/resources/samples/hedis-fpc/FPC_011.xml"
	private static final String VMR_012 = "src/test/resources/samples/hedis-fpc/FPC_012.xml"
	private static final String VMR_013 = "src/test/resources/samples/hedis-fpc/FPC_013.xml"
	private static final String VMR_014 = "src/test/resources/samples/hedis-fpc/FPC_014.xml"
	private static final String VMR_015 = "src/test/resources/samples/hedis-fpc/FPC_015.xml"
	private static final String VMR_016 = "src/test/resources/samples/hedis-fpc/FPC_016.xml"
	private static final String VMR_017 = "src/test/resources/samples/hedis-fpc/FPC_017.xml"
	private static final String VMR_018 = "src/test/resources/samples/hedis-fpc/FPC_018.xml"
 	private static final String VMR_019 = "src/test/resources/samples/hedis-fpc/FPC_019.xml"
	private static final String VMR_020 = "src/test/resources/samples/hedis-fpc/FPC_020.xml"
	private static final String VMR_021 = "src/test/resources/samples/hedis-fpc/FPC_021.xml"
	private static final String VMR_022 = "src/test/resources/samples/hedis-fpc/FPC_022.xml"
	private static final String VMR_023 = "src/test/resources/samples/hedis-fpc/FPC_023.xml"
	private static final String VMR_024 = "src/test/resources/samples/hedis-fpc/FPC_024.xml"
	private static final String VMR_025 = "src/test/resources/samples/hedis-fpc/FPC_025.xml"
	private static final String VMR_026 = "src/test/resources/samples/hedis-fpc/FPC_026.xml"
	private static final String VMR_027 = "src/test/resources/samples/hedis-fpc/FPC_027.xml"
	private static final String VMR_028 = "src/test/resources/samples/hedis-fpc/FPC_028.xml"
	private static final String VMR_029 = "src/test/resources/samples/hedis-fpc/FPC_029.xml"
	private static final String VMR_030 = "src/test/resources/samples/hedis-fpc/FPC_030.xml"
	private static final String VMR_031 = "src/test/resources/samples/hedis-fpc/FPC_031.xml"
	private static final String VMR_032 = "src/test/resources/samples/hedis-fpc/FPC_032.xml"
	private static final String VMR_033 = "src/test/resources/samples/hedis-fpc/FPC_033.xml"
	private static final String VMR_034 = "src/test/resources/samples/hedis-fpc/FPC_034.xml"
	private static final String VMR_035 = "src/test/resources/samples/hedis-fpc/FPC_035.xml"
	private static final String VMR_036 = "src/test/resources/samples/hedis-fpc/FPC_036.xml"
	private static final String VMR_037 = "src/test/resources/samples/hedis-fpc/FPC_037.xml"
	private static final String VMR_038 = "src/test/resources/samples/hedis-fpc/FPC_038.xml"
	private static final String VMR_039 = "src/test/resources/samples/hedis-fpc/FPC_039.xml"
	private static final String VMR_040 = "src/test/resources/samples/hedis-fpc/FPC_040.xml"
	private static final String VMR_041 = "src/test/resources/samples/hedis-fpc/FPC_041.xml"
	private static final String VMR_042 = "src/test/resources/samples/hedis-fpc/FPC_042.xml"
	private static final String VMR_043 = "src/test/resources/samples/hedis-fpc/FPC_043.xml"
	private static final String VMR_044 = "src/test/resources/samples/hedis-fpc/FPC_044.xml"
	private static final String VMR_045 = "src/test/resources/samples/hedis-fpc/FPC_045.xml"
	private static final String VMR_046 = "src/test/resources/samples/hedis-fpc/FPC_046.xml"
	private static final String VMR_047 = "src/test/resources/samples/hedis-fpc/FPC_047.xml"
	private static final String VMR_048 = "src/test/resources/samples/hedis-fpc/FPC_048.xml"
	private static final String VMR_049 = "src/test/resources/samples/hedis-fpc/FPC_049.xml"
	private static final String VMR_050 = "src/test/resources/samples/hedis-fpc/FPC_050.xml"
	private static final String VMR_051 = "src/test/resources/samples/hedis-fpc/FPC_051.xml"
	private static final String VMR_052 = "src/test/resources/samples/hedis-fpc/FPC_052.xml"
	private static final String VMR_053 = "src/test/resources/samples/hedis-fpc/FPC_053.xml"
	private static final String VMR_054 = "src/test/resources/samples/hedis-fpc/FPC_054.xml"
	private static final String VMR_055 = "src/test/resources/samples/hedis-fpc/FPC_055.xml"
	private static final String VMR_056 = "src/test/resources/samples/hedis-fpc/FPC_056.xml"

	@Unroll
	def "test HEDIS TEST FPCv2014_1_0"() {
//		when:
		def input = new File(vmr).text
		def params = [
			kmEvaluationRequest:[scopingEntityId: 'edu.utah', businessId: 'HEDIS_FPC', version: '2014.1.0'],
			specifiedTime: '2012-01-01'
//			specifiedTime: '2014-01-01'
		]
		def responsePayload = OpencdsClient.sendEvaluateAtSpecifiedTimeMessage(params, input)
		println responsePayload

//		then:
		def data = new XmlSlurper().parseText(responsePayload)
		def results = VMRUtil.getResults(data, '\\|')
//		measureFocus.size() == results.measureFocus.size()
//		measureFocus.each {String focus -> 
//			assert results.measureFocus.contains(focus)
//		}
//		assertions.size() == results.assertions.size()
		results.assertions.each {entry ->
			System.err.println "${entry.key} -> ${entry.value}"
		}
		assertions.each {entry ->
			assert results.assertions.containsKey(entry.key)
			if (entry.value) {
				assert results.assertions.get(entry.key) == entry.value
			}
		}
//		numerator == results.measures.numerator
//		denominator == results.measures.denominator
//
//		where:
//		vmr | measureFocus | denominator | numerator | assertions
 
        measures.size() == results.measures.size()
        measures.each {entry ->
            assert results.measures.containsKey(entry.key)
            assert results.measures.get(entry.key).num == entry.value.num
            assert results.measures.get(entry.key).denom == entry.value.denom
        }
        
//		where:
		vmr | assertions | measures
		VMR_000 | ASSRT_000 | MEAS_000
		VMR_001 | ASSRT_001 | MEAS_001
		VMR_002 | ASSRT_002 | MEAS_002
		VMR_003 | ASSRT_003 | MEAS_003
		VMR_004 | ASSRT_004 | MEAS_004
		VMR_005 | ASSRT_005 | MEAS_005
		VMR_006 | ASSRT_006 | MEAS_006
		VMR_007 | ASSRT_007 | MEAS_007
		VMR_008 | ASSRT_008 | MEAS_008
		VMR_009 | ASSRT_009 | MEAS_009
		VMR_010 | ASSRT_010 | MEAS_010
		VMR_011 | ASSRT_011 | MEAS_011
		VMR_012 | ASSRT_012 | MEAS_012
		VMR_013 | ASSRT_013 | MEAS_013
		VMR_014 | ASSRT_014 | MEAS_014
		VMR_015 | ASSRT_015 | MEAS_015	
		VMR_016 | ASSRT_016 | MEAS_016
//		VMR_017 | ASSRT_017 | MEAS_017
//		VMR_018 | ASSRT_018 | MEAS_018
//		VMR_019 | ASSRT_019 | MEAS_019
//		VMR_020 | ASSRT_020 | MEAS_020
//		VMR_021 | ASSRT_021 | MEAS_021
//		VMR_022 | ASSRT_022 | MEAS_022
//		VMR_023 | ASSRT_023 | MEAS_023
//		VMR_024 | ASSRT_024 | MEAS_024
//		VMR_025 | ASSRT_025 | MEAS_025
//		VMR_026 | ASSRT_026 | MEAS_026
//		VMR_027 | ASSRT_027 | MEAS_027
//		VMR_028 | ASSRT_028 | MEAS_028
//		VMR_029 | ASSRT_029 | MEAS_029
//		VMR_030 | ASSRT_030 | MEAS_030
//		VMR_031 | ASSRT_031 | MEAS_031
//		VMR_032 | ASSRT_032 | MEAS_032
//		VMR_033 | ASSRT_033 | MEAS_033
//		VMR_034 | ASSRT_034 | MEAS_034
//		VMR_035 | ASSRT_035 | MEAS_035
//		VMR_036 | ASSRT_036 | MEAS_036
//		VMR_037 | ASSRT_037 | MEAS_037
//		VMR_038 | ASSRT_038 | MEAS_038
//		VMR_039 | ASSRT_039 | MEAS_039
//		VMR_040 | ASSRT_040 | MEAS_040
//		VMR_041 | ASSRT_041 | MEAS_041
//		VMR_042 | ASSRT_042 | MEAS_042
//		VMR_043 | ASSRT_043 | MEAS_043
//		VMR_044 | ASSRT_044 | MEAS_044
//		VMR_045 | ASSRT_045 | MEAS_045
//		VMR_046 | ASSRT_046 | MEAS_046
//		VMR_047 | ASSRT_047 | MEAS_047
//		VMR_048 | ASSRT_048 | MEAS_048
//		VMR_049 | ASSRT_049 | MEAS_049
//		VMR_050 | ASSRT_050 | MEAS_050
//		VMR_051 | ASSRT_051 | MEAS_051
//		VMR_052 | ASSRT_052 | MEAS_052
//		VMR_053 | ASSRT_053 | MEAS_053
//		VMR_054 | ASSRT_054 | MEAS_054
//		VMR_055 | ASSRT_055 | MEAS_055
//		VMR_056 | ASSRT_056 | MEAS_056
		
		}
}