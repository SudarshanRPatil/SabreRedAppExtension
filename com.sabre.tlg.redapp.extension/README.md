## Why / What
Why  

- TLG Agent App enables the agents to make bookings on pinSIGHT by writing custom postfixed to GDS commands. 


What

- Plugin Project for Sabre RedApp exposed on GitHub
- README.md File
- Published App on RAC 

## OverView

TLG Agent App enables the agents to make bookings on pinSIGHT by writing custom postfixed to GDS commands. 
The native Sabre commands are customized to redirect the requests to pinSIGHT web version, 
providing the agents new booking experience along with the familiarity of GDS console.

# Command for tab browser:

Run this command to search for hotels in a given city, for the given dates and for given no. of travellers. 
It opens the search results in a tab browser of the application.
Example1 :- HOTLAS/11SEP-13SEP2*PS (END with *PS)
Example2 :- HOTLAS/11SEP-2NT2*PS (END with *PS)

# Command to get agent profile: 

Run this command to search for the agent's profile. 
It returns first name, last name, agency name, agent ID, email, PCC, parent PCC, phone number etc. information about the logged in agent.
Example :- AGTPROF (CUSTOM COMMAND pattern)

# Configuration Setting Url: 
https://docs.google.com/spreadsheets/d/1XMIVYLtJYqWMDxbqN9EGbs4nmhv9qB0t9e3PnatPYsw

# Support

- [Stack Overflow](http://stackoverflow.com/questions/tagged/sabre "Stack Overflow")
- Need to report an issue/improvement? Use the built-in [issues] (https://github.com/SabreDevStudio/SACS-Java/issues) section
- [Sabre Dev Studio](https://developer.sabre.com/)

# Disclaimer of Warranty and Limitation of Liability
This software and any compiled programs created using this software are furnished without warranty of any kind, including but not limited to the implied warranties of merchantability and fitness for a particular purpose. No oral or written information or advice given by Sabre, its agents or employees shall create a warranty or in any way increase the scope of this warranty, and you may not rely on any such information or advice. Sabre does not warrant, guarantee, or make any representations regarding the use, or the results of the use, of this software, compiled programs created using this software, or written materials in terms of correctness, accuracy, reliability, currentness, or otherwise. The entire risk as to the results and performance of this software and any compiled applications created using this software is assumed by you. Neither Sabre nor anyone else who has been involved in the creation, production or delivery of this software shall be liable for any direct, indirect, consequential, or incidental damages (including damages for loss of business profits, business interruption, loss of business information, and the like) arising out of the use of or inability to use such product even if Sabre has been advised of the possibility of such damages.