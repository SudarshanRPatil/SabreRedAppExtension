package com.tlg.pinsight.listeners;

import com.sabre.edge.cf.emu.data.EmulatorCommand;
import com.sabre.edge.cf.emu.data.requests.EmulatorCommandRequest;
import com.sabre.edge.cf.emu.data.responses.EmulatorCommandResponse;
import com.sabre.edge.cf.model.IEvent;
import com.sabre.edge.cf.model.IEventListener;
import com.sabre.edge.cf.model.ServiceStatus;
import com.sabre.edge.cf.model.element.ServiceContext;
import com.tlg.pinsight.managers.ConfigurationManager;
import com.tlg.pinsight.managers.PnrManager;
import com.tlg.pinsight.managers.RedirectionManager;
import com.tlg.pinsight.ui.CfServicesHelper;


public class CommandListener implements IEventListener {

	public void handleEvent(IEvent evt) {

		// Get context to read emulator command text.
		ServiceContext sc = (ServiceContext) evt.getContext();
		EmulatorCommandRequest cmdRQ = (EmulatorCommandRequest) sc.getRequest();
		EmulatorCommandResponse cmdRS = (EmulatorCommandResponse) sc.getResponse();

		// Get the console command
		EmulatorCommand cmd = cmdRQ.getEmulatorCommand();

		String cmdTyped = cmd.getCommand();

		String cmdResp = cmdRS.getEmulatorResponse() != null ? cmdRS
				.getEmulatorResponse().getResponse() : "";

		sc.setResponse(cmdRS);

		sc.setStatus(ServiceStatus.SUCCESS);
		
		//Check and Handle command response

		if (cmdResp.equalsIgnoreCase(Constants.SIGN_IN_A)) {
			CfServicesHelper.showInEmulator(Constants.Need_Sign_In_Message);
		}

		if (cmdTyped != null && cmdTyped.isEmpty() == false) {
			
			if (cmdTyped.equalsIgnoreCase(Constants.Context)) // TODO: Need to fetch the commands from configuration
			{
				CfServicesHelper.showInEmulator(Constants.Load_configuration_Message);
				ConfigurationManager configurationManager = new ConfigurationManager();
				configurationManager.getConfiguration(Constants.Configuration_url);
			}

			// E,ER,EM,ET - Need to add PNR details at time of End Transaction

			if (isEndTransactionCommand(cmdTyped)) {
				CfServicesHelper.showInEmulator(Constants.Save_PNR_Message);
				PnrManager pnrManager = new PnrManager();
				pnrManager.savePNRDetails(evt, cmdTyped, cmdResp);
			}

			// To check command post_fix
			
			String lastChars = getLastChars(cmdTyped);
			
			RedirectionManager redirectionManager = new RedirectionManager();
			
			if (lastChars.equalsIgnoreCase(Constants.Ps_internal_browser)) // TODO: Need to fetch the commands from configuration
			{
				redirectionManager.runInternalBrowserWorkFlow(evt, cmdTyped, cmdResp);
			} else if (lastChars.contains(Constants.Ps_external_browser)) // TODO: Need to fetch the commands from configuration
			{
				redirectionManager.runExternalBrowserWorkFlow(evt, cmdTyped, cmdResp);
			}

		} else {
			CfServicesHelper.showInEmulator(Constants.Invalid_Command);
		}
	}

	public String getLastChars(String cmd) {
		if (cmd.length() == 3) {
			return cmd;
		} else {
			return cmd.substring(cmd.length() - 3);
		}
	}
	
	public boolean isEndTransactionCommand(String cmdTyped) {
		return cmdTyped.equalsIgnoreCase("E")
				|| cmdTyped.equalsIgnoreCase("ER")
				|| cmdTyped.equalsIgnoreCase("EM")
				|| cmdTyped.equalsIgnoreCase("ET");
	}

}
