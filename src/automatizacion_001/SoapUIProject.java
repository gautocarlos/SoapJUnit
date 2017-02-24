package automatizacion_001;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.StandaloneSoapUICore;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.tools.SoapUITestCaseRunner;

public class SoapUIProject {

	@Test
	public void pruebaSoapUIRunnerReporte() throws Exception {

		String suiteName = "";
		String reportStr = "";

		// variables for getting duration
		long startTime = 0;
		long duration = 0;

		TestRunner runner = null;

		List<TestSuite> suiteList = new ArrayList<TestSuite>();
		List<TestCase> caseList = new ArrayList<TestCase>();

		SoapUI.setSoapUICore(new StandaloneSoapUICore(true));

		// specified soapUI project
		WsdlProject project = new WsdlProject("proyectos_soapui/0000-AUT-001_soapui-project.xml");

		// get a list of all test suites on the project
		suiteList = project.getTestSuiteList();

		// you can use for each loop
		for (int i = 0; i < suiteList.size(); i++) {

			// get name of the "i" element in the list of a test suites
			suiteName = suiteList.get(i).getName();
			reportStr = reportStr + "\nTest Suite: " + suiteName;

			// get a list of all test cases on the "i"-test suite
			caseList = suiteList.get(i).getTestCaseList();

			for (int k = 0; k < caseList.size(); k++) {

				startTime = System.currentTimeMillis();

				// run "k"-test case in the "i"-test suite
				runner = project.getTestSuiteByName(suiteName).getTestCaseByName(caseList.get(k).getName())
						.run(new PropertiesMap(), false);

				duration = System.currentTimeMillis() - startTime;

				reportStr = reportStr + "\n\tTestCase: " + caseList.get(k).getName() + "\n\t\tStatus: "
						+ runner.getStatus() + "\tReason: " + runner.getReason() + "\tDuration: " + duration;
			}

		}

		// string of the results
		System.out.println(reportStr);
	}

	// @Test
	public void pruebaSoapUIRunner() throws Exception {
		SoapUITestCaseRunner runner = new SoapUITestCaseRunner();
		runner.setSettingsFile("config/soapui-settings.xml");
		runner.setProjectFile("proyectos_soapui/SOAP_REST_Ejemplos_EE-GEDO.xml");
		runner.run();
	}
}