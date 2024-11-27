package pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.testng.Assert;
import org.testng.Assert;

import testbase.BasePage;
import utilities.PropertiesOperations;

public class NavigationPage extends BasePage {
	@FindBy(linkText = "Revenue Calculator")
	private WebElement revenueCalculatorLink;
	@FindBy(className = "css-79elbk")
	private WebElement sliderSection;
	@FindBy(css = "input[type='range']")
	private WebElement sliderInput;
	@FindBy(css = ".MuiSlider-thumb")
	private WebElement sliderThumb;
	@FindBy(css = ".MuiSlider-track")
	public WebElement sliderTrack;
	@FindBy(xpath = "//div[@class='MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-formControl MuiInputBase-sizeSmall css-129j43u']//input[@type='number']")
	public WebElement inputElement;
	@FindBy(xpath = "//div[contains(@class, 'MuiBox-root') and contains(., 'CPT-')]")
	private List<WebElement> cptCards;
	@FindBy(xpath = "//div[@class='MuiToolbar-root MuiToolbar-gutters MuiToolbar-regular css-1lnu3ao']//p[contains(@class, 'MuiTypography-body2')][4]")
	private WebElement totalReimbursementHeader;
	@FindBy(xpath = "//div[contains(@class, 'MuiBox-root') and contains(., 'CPT-')]")
	private List<WebElement> cptCardUnchecked;

	public NavigationPage() {
		PageFactory.initElements(driver, this);
	}

	public void navigateHome() throws Exception {
		String url = PropertiesOperations.getPropertyValueByKey("url");
		driver.navigate().to(url);
	}

	public void navigateToRevenuePage() throws Exception {
		wait.until(ExpectedConditions.visibilityOf(revenueCalculatorLink));
		revenueCalculatorLink.click();
		wait.until(ExpectedConditions.visibilityOf(sliderSection));
	}

	public void scrollToSliderSection() throws Exception {
		js.executeScript("arguments[0].scrollIntoView(true);", sliderSection);
		wait.until(ExpectedConditions.visibilityOf(sliderSection));
	}

	public void adjustSlider() throws Exception {

		int newValue = 820;
		int minValue = Integer.parseInt(sliderInput.getAttribute("min"));
		int maxValue = Integer.parseInt(sliderInput.getAttribute("max"));
		// Validate that the value is within the range
		if (newValue < minValue || newValue > maxValue) {
			System.out.println("The value " + newValue + " is out of range.");
			return;
		}
		// Calculate the percentage of the new value
		double percentage = ((double) (newValue - minValue) / (maxValue - minValue)) * 100;
		js.executeScript("arguments[0].setAttribute('value', '" + newValue + "');", sliderInput);
		js.executeScript("arguments[0].setAttribute('aria-valuenow', '" + newValue + "');", sliderInput); // Update
		js.executeScript("arguments[0].style.left='" + percentage + "%';", sliderThumb); // Update thumb position
		js.executeScript("arguments[0].style.width='" + percentage + "%';", sliderTrack); // Update track width
		js.executeScript(
				"arguments[0].value = arguments[1];"
						+ "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
						+ "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
				inputElement, newValue);
		wait.until(ExpectedConditions.attributeToBe(inputElement, "value", String.valueOf(newValue)));
		String ariaValue = sliderInput.getAttribute("aria-valuenow");
        System.out.println("Slider position: " + ariaValue);
		// Verify the change
		String updatedValue = inputElement.getAttribute("value");
		Assert.assertEquals(String.valueOf(newValue), updatedValue, "Values do not match!");
		System.out.println("Text in inputfield on sliderThumb moved is :" + updatedValue);
	}

	public void updateTextField() throws Exception {
		// Clear the input field
				inputElement.clear();
				Thread.sleep(2000);
				// Set the new slider value
				int newValue1 = 560;
				int minValue = Integer.parseInt(sliderInput.getAttribute("min"));
				int maxValue = Integer.parseInt(sliderInput.getAttribute("max"));
				double percentage1 = ((double) (newValue1 - minValue) / (maxValue - minValue)) * 100;
				js.executeScript(
		                "arguments[0].value = arguments[1];" +
		                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
		                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
		                inputElement, newValue1
		            );
		        js.executeScript("arguments[0].setAttribute('value', '" + newValue1 + "');", sliderInput);
		        js.executeScript("arguments[0].setAttribute('aria-valuenow', '" + newValue1 + "');", sliderInput); // Update aria-valuenow
		        js.executeScript("arguments[0].style.left='" + percentage1 + "%';", sliderThumb); // Update thumb position
		        js.executeScript("arguments[0].style.width='" + percentage1 + "%';", sliderTrack); // Update track width
		        Thread.sleep(1000);
		        String ariaValueNow = sliderInput.getAttribute("aria-valuenow");
		        System.out.println("Slider position: " + ariaValueNow);
		        
		        // Fetch the 'value' attribute value
		        String value = inputElement.getAttribute("value");
		        Thread.sleep(1000);
		        System.out.println("Value in the input field: " + value);
		        Thread.sleep(1000);
				
			}
	public void selectCheckBoxes() throws Exception {
		WebElement cptElement = driver.findElement(By.cssSelector(".MuiBox-root.css-1p19z09"));
		js.executeScript("arguments[0].scrollIntoView(true);", cptElement);
		wait.until(ExpectedConditions.visibilityOf(cptElement));
		for (WebElement cptCard : cptCards) {
			String cptCode = cptCard.findElement(By.xpath(".//*[contains(text(), 'CPT-')]")).getText();
			if (cptCode.equals("CPT-99091") || cptCode.equals("CPT-99453") || cptCode.equals("CPT-99454")
					|| cptCode.equals("CPT-99474")) {
				WebElement checkbox = cptCard.findElement(By.xpath(".//input[@type='checkbox']"));
				if (!checkbox.isSelected()) {
					checkbox.click();
					System.out.println("Clicked checkbox for: " + cptCode);
				}
			}
		}
	}

	public void validateAndVerifyTotalRecurringReimbursement() throws Exception {
		for (WebElement cptCard : cptCardUnchecked) {
			WebElement checkbox = cptCard.findElement(By.xpath(".//input[@type='checkbox']"));
			if (!checkbox.isSelected()) {
				checkbox.click();
				String cptCode = cptCard.findElement(By.xpath(".//*[contains(text(), 'CPT-')]")).getText();
				System.out.println("Checked checkbox for CPT code: " + cptCode);
			}
		}

		wait.until(ExpectedConditions.visibilityOf(totalReimbursementHeader));
		js.executeScript("arguments[0].scrollIntoView(true);", totalReimbursementHeader);
		String displayedValue = totalReimbursementHeader.getText();
		System.out.println("Displayed Value: " + displayedValue);
		String expectedValue = "$110700";
		if (displayedValue.equals(expectedValue)) {
			System.out.println("Validation passed: The displayed value matches the expected value.");
		} else {
			System.out
					.println("Validation failed: Expected value is " + expectedValue + ", but found " + displayedValue);
		}

	}
}
