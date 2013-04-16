// package src;


import processing.core.PApplet;
import rwmidi.*;

public class MidiControl extends PApplet{
	//
	private static MidiControl instance = null;
	//
	DataProfile dataProfile;
	//
	PApplet pApp;
	
	/// MIDI
	MidiInput input;
	MidiOutput output;
	
	int countMax = 1;
	int theCounter = countMax;
	// this makes sure we don't overload 
	// with midi signals and freeze the output

	int[] SoundArray = { 62, 63, 60, 61, 64, 46, 120, 38, 40, 62, 63, 60, 61, 64, 46, 120, 38, 40}; // 40
	
	int midiChannel = 0; // channel that we send the midi on
	
	float theValue = 0; /// controller value
	
	protected MidiControl() {
	      // Exists only to defeat instantiation.
	}
	 public static MidiControl getInstance() {
	      if(instance == null) {
	         instance = new MidiControl();
	      }
	      return instance;
	 }
	   
	
	public void initMidi(){
		// init our instance of the data profile
		dataProfile =  DataProfile.getInstance();
		pApp = dataProfile.pApp;
		
		 input = RWMidi.getInputDevices()[midiChannel].createInput(this);
		 output = RWMidi.getOutputDevices()[midiChannel].createOutput();
		 
	

		
	}
	

	/////////////////////////////////
	////init MIDI tracking //////////
	////////////////////////////////////
	public void controllerChangeReceived(rwmidi.Controller cntrl){
		println("cc recieved"+ cntrl.getCC() + " value" + cntrl.getValue() +  "inpug: " + cntrl.getInput());

		if(cntrl.getCC() == 2){
		
		}
		
		if(cntrl.getCC() == 3){
		
		}
		
		if(cntrl.getCC() ==4){
		
		}
		
		if(cntrl.getCC() == 5){ //// adjust skip
		
		}
		
		if(cntrl.getCC() == 6){ /// adjus scale
		
		}
	}
	
	/// notes
	public void noteOnReceived(Note note) {
	
		println("note on " + note.getPitch());
		if(note.getPitch() == 76){
		// modThresh1(note.getPitch());
		}
		if(note.getPitch() == 77){
		// modThresh2(note.getPitch());
		}
		
		if(note.getPitch() == 78){
		// modThresh3(note.getPitch());
		}
	}
	void sysexReceived(rwmidi.SysexMessage msg) {
		println("sysex " + msg);
	}
	
	/// end MIDI tracking //////////////
	////////////////////////////////////
////MIDI SENDING ///////
	
	public void doPitchChange(float v){
		theValue = v;
		
		try{
			if(theCounter<=0){
				float newV = pApp.map(v, -2,53, 1,127);
				/// MidiOutput.sendController(int channel, int cc, int value) - int
				println("pitchChange: " + (int)theValue);
				int retctrl = output.sendController(midiChannel, 99, (int)theValue);
				
				sendMidiNote();
				
				int retctrl1 = output.sendController(midiChannel, 98, (int)theValue);
				sendMidiNote();
				/*
				int retctrl2 = output.sendController(midiChannel, 98, (int)theValue);
				sendMidiNote();
				// retctrl = output.sendSysex(new byte[] {(byte)0xF0, 1, 2, 3, 4, (byte)0xF7});
				 * */
				/// 
				// int ret = output.sendNoteOn(midiChannel, 62, 127);
				// ret = output.sendSysex(new byte[] {(byte)0xF0, 1, 2, 3, 4, (byte)0xF7});
			}
		
		} catch(Exception e){
			
			println("controller error");
		}
		
	}
	public void checkMidiCounter(){
		if(theCounter<=0){
			// theCounter = countMax;
		} else {
			theCounter -=1;
			
		}
	}
	public void sendMidiNote(){
		//// MidiOutput.sendNoteOn(int channel, int note, int velocity) - int
		//  20,30,40,62,63,60,61,64,46
		try{
			if(theCounter<=0){
				println("Sending Midi to device");
				int ret = output.sendNoteOn(midiChannel, 63, 127);
				// ret = output.sendNoteOff(0, 40, 127);
				theCounter = countMax;
			} 
		// ret = output.sendSysex(new byte[] {(byte)0xF0, 1, 2, 3, 4, (byte)0xF7});
		} catch (Exception e){
			println("Midi note error");
		}
	}
	public void sendMultiNote(int note){
		//// MidiOutput.sendNoteOn(int channel, int note, int velocity) - int
		int n = note;
		try{
			println("note: " + n);
			if(theCounter<=0){
				println("Sending Midi to device" + n);
				int ret = output.sendNoteOn(midiChannel, SoundArray[n], 127);
				// ret = output.sendNoteOff(0, 40, 127);
				theCounter = countMax;
			} 
		// ret = output.sendSysex(new byte[] {(byte)0xF0, 1, 2, 3, 4, (byte)0xF7});
		} catch (Exception e){
			println("Midi note error");
		}
		
	}
	
	
//////	

}
