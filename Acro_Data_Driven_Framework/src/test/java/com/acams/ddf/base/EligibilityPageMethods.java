package com.acams.ddf.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class EligibilityPageMethods extends BaseTest  {
	
	//BaseTest bt=new BaseTest();

	
	public void selectHippaConsent(String selectHippaConsentyesOrNo,ExtentTest testObject) {
		try {
			scrollTo("HIPPAconsentYes_id", testObject);
			boolean HIPPAconsentVisible = waitUntilElementPresent("HIPPAconsentYes_id", testObject);
			if (HIPPAconsentVisible == true) {

				if (selectHippaConsentyesOrNo.equalsIgnoreCase("Yes")) {
					WebElement e = getElement("HIPPAconsentYes_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected HIPPAConsent as NO");

				} else if (selectHippaConsentyesOrNo.equalsIgnoreCase("No")) {
					WebElement e = getElement("HIPPAconsentNO_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected HIPPAConsent as YES");

				}
			}

		} catch (Exception e) {
			testObject.log(Status.INFO,"selectHippaConsent catch block got executed"+ e.fillInStackTrace());
		}

	}
	
	public void selectReleaseInformation(String selectReleaseInformationyesOrNo,ExtentTest testObject) {
		try {
			scrollTo("releaseinfoYes_id", testObject);
			boolean releaseInfoVisible = waitUntilElementPresent("releaseinfoYes_id", testObject);
			if (releaseInfoVisible == true) {

				if (selectReleaseInformationyesOrNo.equalsIgnoreCase("Yes")) {
					WebElement e = getElement("releaseinfoYes_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected ReleaseInformation as YES");

				} else if (selectReleaseInformationyesOrNo.equalsIgnoreCase("No")) {
					WebElement e = getElement("releaseinfoNo_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected ReleaseInformation as NO");

				}
			}

		} catch (Exception e) {
			testObject.log(Status.INFO,"selectReleaseInformationyesOrNo catch block got executed"+ e.fillInStackTrace());
		}

	}
	
	
	public void selectMeetsIncomeGuideline(String MeetsIncomeGuidelineyesOrNo,ExtentTest testObject) {
		try {
			scrollTo("meetincomeradiobuttonYes_id", testObject);
			boolean meetIncomeGuidelineVisible = waitUntilElementPresent("meetincomeradiobuttonYes_id", testObject);
			if (meetIncomeGuidelineVisible == true) {

				if (MeetsIncomeGuidelineyesOrNo.equalsIgnoreCase("Yes")) {
					WebElement e = getElement("meetincomeradiobuttonYes_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected Meet Income GuideLine as YES");

				} else if (MeetsIncomeGuidelineyesOrNo.equalsIgnoreCase("No")) {
					WebElement e = getElement("meetincomeradiobuttonNo_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected Meet Income GuideLine as NO");

				}
			}

		} catch (Exception e) {
			testObject.log(Status.INFO,"selectMeetsIncomeGuideline catch block got executed"+ e.fillInStackTrace());
		}

	}
	
	public void selectApprovedByCc(String selectApprovedByCcyesOrNo,ExtentTest testObject) {
		try {
			scrollTo("approvedByCCYes_id", testObject);
			boolean approvedByCCVisible= waitUntilElementPresent("approvedByCCYes_id", testObject);
			if (approvedByCCVisible == true) {

				if (selectApprovedByCcyesOrNo.equalsIgnoreCase("Yes")) {
					WebElement e = getElement("approvedByCCYes_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected the Approved By CC as YES");

				} else if (selectApprovedByCcyesOrNo.equalsIgnoreCase("No")) {
					WebElement e = getElement("approvedbyCCNo_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected the Approved By CC as NO");

				}
			}

		} catch (Exception e) {
			testObject.log(Status.INFO,"selectApprovedByCc catch block got executed"+ e.fillInStackTrace());
		}

	}
	
	
	public void selectPAForDiagnosis(String selectpaForDoagnosisYesOrNo,ExtentTest testObject) {
		try {
			scrollTo("PAfordiagnosisYes_id", testObject);
			boolean paForDoagnosisVisible= waitUntilElementPresent("PAfordiagnosisYes_id", testObject);
			if (paForDoagnosisVisible == true) {

				if (selectpaForDoagnosisYesOrNo.equalsIgnoreCase("Yes")) {
					WebElement e = getElement("PAfordiagnosisYes_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected the PA for Diagnosis as YES");
				} else if (selectpaForDoagnosisYesOrNo.equalsIgnoreCase("No")) {
					WebElement e = getElement("PAforDiagnosisNo_id", testObject);
					e.click();
					testObject.log(Status.INFO, "Successfully selected the PA for Diagnosis as NO");
				}
			}else{
				clickDemographics(testObject);
				
			}

		} catch (Exception e) {
			testObject.log(Status.INFO,"selectPAForDiagnosis catch block got executed"+ e.fillInStackTrace());
		}

	}
	public void clickEligibility(ExtentTest testObject) {
		
		try{
		waitUntilElementPresent("clickeligibility_xpath", testObject);
		testObject.log(Status.INFO, "Clicking on the eligibility tab");
		WebElement e=getElement("clickeligibility_xpath", testObject);
		e.click();
		testObject.log(Status.INFO,
				"successfully clicked on the eligibility tab");
		}catch (Exception e) {
			testObject.log(Status.INFO,"clickEligibility catch block got executed"+ e.fillInStackTrace());
		}

	} 
	
	
	public void addIcdCode(String textToSelect, String LocatorKey, String data, ExtentTest testObject)
	{
		try{
			click("medicaldiagnosisaddicon_xpath", testObject);
			type("icdcode_id", data, testObject);		
			selectTextInAutocompleteTextbox("A000", "icdcodelist_id", testObject);		
			getCurrentDate("diagnosisdate_id", testObject);
			click("cmsapproved_id", testObject);
			type("ICDccomments_id", "ICD code added successfully", testObject);
			scrollTo("icdaddbutton_id", testObject);
			click("icdaddbutton_id", testObject);
			
			waitUntilElementPresent("icdnameingrid_xpath", testObject);
			List<WebElement> icdInformationGridRow = driver.findElements(By
					.xpath(prop.getProperty("icdnameingrid_xpath")));
			int numberOfRowsInGrid = icdInformationGridRow.size();
			System.out.println(numberOfRowsInGrid);
			if (numberOfRowsInGrid != 0) {
				scrollTo("icdnameingrid_xpath", testObject);
				testObject.log(Status.PASS, "record  found in the ICD Grid:- "
						+ numberOfRowsInGrid);
				reportPass("record  found in the ICD Grid, Test Case Passed",
						testObject);
			} else {
				testObject.log(Status.FAIL, "record not found in the ICD Grid : - "
						+ numberOfRowsInGrid);
				reportFailure("record not found in the ICD, Test Case Failed",
						testObject);
			}
		        

	
		}catch (Exception e) {
			testObject.log(Status.INFO,"addIcdCode catch block got executed"+ e.fillInStackTrace());
		}
		
	}
	
	
}
