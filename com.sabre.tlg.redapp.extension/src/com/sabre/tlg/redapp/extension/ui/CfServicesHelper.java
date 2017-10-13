package com.sabre.tlg.redapp.extension.ui;

import com.sabre.edge.cf.core.registry.service.ISRWCommunication;
import com.sabre.edge.cf.emu.data.EmulatorCommand;
import com.sabre.edge.cf.emu.data.requests.EmulatorCommandRequest;
import com.sabre.edge.cf.emu.external.ExecuteInEmuServiceClient;
import com.sabre.edge.cf.emu.external.ShowInEmuServiceClient;
import com.sabre.tlg.redapp.extension.services.CoreServicesHelper;

public class CfServicesHelper {
	
  public static void executeInEmulator(String cmdToExecute ){
		
		ISRWCommunication comm = CoreServicesHelper.getISRWCommunication();
		EmulatorCommand cmd = new EmulatorCommand(cmdToExecute);
		System.out.println("sending simple text to host");
		cmd.setShowResponse(false);
		//cmd.setCommand("executed sabre actual command : " + cmdToExecute);
		//cmd.setIsCommand(true);
		EmulatorCommandRequest rq = new EmulatorCommandRequest(cmd);
		ExecuteInEmuServiceClient execEmu = new ExecuteInEmuServiceClient(comm);
		execEmu.send(rq);
				
	}
	
	public static void showInEmulator(String cmdToExecute){
		
		ISRWCommunication comm = CoreServicesHelper.getISRWCommunication();
		EmulatorCommand cmd = new EmulatorCommand(cmdToExecute);
		cmd.setIsCommand(false);
		cmd.setCommand(cmdToExecute);
		//cmd.setShowCommand(true);
		cmd.setShowResponse(false);
		EmulatorCommandRequest rq = new EmulatorCommandRequest(cmd);
		ShowInEmuServiceClient showInEmu = new ShowInEmuServiceClient(comm);
		
		showInEmu.send(rq);	
		
	}
}
