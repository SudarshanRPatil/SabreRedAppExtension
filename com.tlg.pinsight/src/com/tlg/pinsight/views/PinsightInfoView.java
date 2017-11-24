package com.tlg.pinsight.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class PinsightInfoView extends ViewPart {

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		createContents(parent);
	}

	@Override
	public void setFocus() {

	}

	private void createContents(Composite parent) {

		Composite compMain = new Composite(parent, SWT.NONE);
		compMain.setLayout(new GridLayout(1, true));

		Label txtContent = new Label(compMain, SWT.WRAP);
		txtContent.setText("About pinSIGHT App\n\n");

		txtContent.setAlignment(SWT.CENTER);

		Label txtContent1 = new Label(compMain, SWT.WRAP);
		txtContent1
				.setText("Overview:\r\n\n\t TLG Agent App enables the agents to make bookings on pinSIGHT by writing custom postfixed to GDS commands. "
						+ "The native Sabre commands are customized to redirect the requests to pinSIGHT web version, providing the agents new booking experience along with the familiarity of GDS console.\r\n\t");

		txtContent1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));

		Label txtContent2 = new Label(compMain, SWT.WRAP);
		txtContent2
				.setText("Sample commands for tab browser:\r\n\n\t Run this command to search for hotels in a given city, for the given dates and for given no. of travellers. "
						+ "It opens the search results in a tab browser of the application.(Ends with [\"*PS\"])\r\n\n\t "
						+ "1.Example :- HOTLAS/11DEC-2NT2*PS --> (Hotel search for 2nights 2 passengers for a city)\n\n\t"
						+ "2.Example :- HOTLAS/11DEC-22DEC2*PS --> (Hotel search with check-in and check-out date for 2 passengers for a city)\n\n\t"
						+ "3.Example :- HOTLAS/20DEC-22DEC2/HH*PS --> (With a specific hotel chain code (HH))\n\n\t"
						+ "4.Example :- HOTFL-DISNEY WORLD/12DEC-20DEC2*PS --> (Basic Hotel Search with points of interest for the United States)\n\n\t"
						+ "5.Example :- HOTC/FR-LOUVRE/12DEC-20DEC2*PS --> (Basic Hotel Search with points of interest for countries other than United States)\n\n\t"
						+ "6.Example :- HOTLAS/12DEC-15DEC2/N-HYATT*PS --> (Basic Hotel Search by Hotel name)\n\n\t"
						+ "7.Example :- HOTLAS/12DEC-15DEC2/D-10*PS --> (Basic Hotel Search by Distance)\n\n\t"
						+ "8.Example :- HOTLAX/10DEC-15DEC2/PC-90045*PS --> (Basic Hotel Search by zip code)\n\n\t"
						+ "9.Example :- HOTLAS¥TL7/10DEC-13DEC1*PS --> (Basic Hotel Search by Rate Code)\n\n\t");
		txtContent2.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));

		Label txtContent4 = new Label(compMain, SWT.WRAP);
		txtContent4
				.setText("Sample commands to fetch agent profile:\r\n\n\t Run this command to search for the agent's profile. "
						+ "It returns first name, last name, agency name, agent ID, email, PCC, parent PCC, phone number etc. "
						+ "information about the logged in agent.\r\n\t Example :- AGTPROF (Custom Command pattern)\n\n");
		txtContent4.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));

		Label txtContent5 = new Label(compMain, SWT.WRAP);
		txtContent5
				.setText("Configuration Setting:\r\n\n\t Please refer Url : https://docs.google.com/spreadsheets/d/1XMIVYLtJYqWMDxbqN9EGbs4nmhv9qB0t9e3PnatPYsw");
		txtContent5.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
	}
}
