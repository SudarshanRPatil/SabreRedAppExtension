package com.tlg.pinsight.ui;

import com.sabre.edge.cf.core.registry.service.ISRWCommunication;
import com.sabre.edge.cf.emu.data.EmulatorCommand;
import com.sabre.edge.cf.emu.data.requests.EmulatorCommandRequest;
import com.sabre.edge.cf.emu.external.ExecuteInEmuServiceClient;
import com.sabre.edge.cf.emu.external.ShowInEmuServiceClient;
import com.tlg.pinsight.services.CoreServiceProvider;

public class CfServicesHelper {

	public static void executeInEmulator(String cmdToExecute) {

		ISRWCommunication comm = CoreServiceProvider.getISRWCommunicationService();
		EmulatorCommand cmd = new EmulatorCommand(cmdToExecute);
		cmd.setShowResponse(false);
		EmulatorCommandRequest rq = new EmulatorCommandRequest(cmd);
		ExecuteInEmuServiceClient execEmu = new ExecuteInEmuServiceClient(comm);
		execEmu.send(rq);
	}

	public static void showInEmulator(String cmdToExecute) {

		ISRWCommunication comm = CoreServiceProvider.getISRWCommunicationService();
		EmulatorCommand cmd = new EmulatorCommand(cmdToExecute);
		cmd.setIsCommand(false);
		cmd.setCommand(cmdToExecute);
		cmd.setShowResponse(false);
		EmulatorCommandRequest rq = new EmulatorCommandRequest(cmd);
		ShowInEmuServiceClient showInEmu = new ShowInEmuServiceClient(comm);
		showInEmu.send(rq);
	}
}
