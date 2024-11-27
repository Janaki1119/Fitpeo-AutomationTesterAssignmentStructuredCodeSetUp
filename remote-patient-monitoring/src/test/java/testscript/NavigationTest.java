package testscript;

import org.testng.Assert;
import org.testng.annotations.Test;

import testbase.BasePage;

public class NavigationTest extends BasePage {
	@Test
	public void navigate_To_RevenueCalculatorPage() throws Exception {
		System.out.println("Test Case1" + "\n" + "*************************");
		navigationPage.navigateHome();
		String actualTitle = driver.getTitle();
		String expectedTitle = "Remote Patient Monitoring (RPM) - fitpeo.com";
		Assert.assertEquals(actualTitle, expectedTitle, "Title does not match!");
		System.out.println("Navigation to FitPeo Homepage Successfull!");
		System.out.println("Test Case2" + "\n" + "*************************");
		navigationPage.navigateToRevenuePage();
		String actualUrl = driver.getCurrentUrl();
		String expectedUrl = "https://www.fitpeo.com/revenue-calculator";
		Assert.assertEquals(actualUrl, expectedUrl, "Failed to navigate to Revenue Calculator page!");
		System.out.println("Navigation to Revenue Calculator page verified successfully.");
		System.out.println("Test Case3" + "\n" + "*************************");
		System.out.println("Scrolled to slidersection");
		navigationPage.scrollToSliderSection();
		System.out.println("Test Case4 " + "\n" + "*************************");
		navigationPage.adjustSlider();
		System.out.println("Test Case5 and Test Case6" + "\n" + "*************************");
		navigationPage.updateTextField();
		System.out.println("Test Case7" + "\n" + "*************************");
		System.out.println(
				"selecting only  the checkboxes for CPT-99091, CPT-99453, CPT-99454, and CPT-99474 in selectCheckBoxes() Method ");
		navigationPage.selectCheckBoxes();
		System.out.println("Test Case8 and Test Case9" + "\n" + "*************************");
		System.out.println("selecting the checkboxes other than the following CPT-99091, CPT-99453, CPT-99454, and CPT-99474 in validateAndVerifyTotalRecurringReimbursement() Method ");
		navigationPage.validateAndVerifyTotalRecurringReimbursement();
	}

}
