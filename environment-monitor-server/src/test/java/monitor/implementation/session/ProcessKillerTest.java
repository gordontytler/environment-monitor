package monitor.implementation.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import monitor.implementation.shell.SSHExecuter;
import monitor.model.Command;
import monitor.model.CommandResult;
import monitor.model.CommandStatus;
import monitor.model.Configuration;
import monitor.model.Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ProcessKillerTest {

	static String host = Configuration.getInstance().getDefaultHostForTests();
	static Server server = new Server(host);
	
	private AllSessionPools allSessionPools = AllSessionPools.getInstance();
	
	Session session;
	Session controlSession;		
	

	@Before
	public void setUp() {
		allSessionPools.getServerSessionPool(server).destroySSHConnections();
		session = allSessionPools.getServerSessionPool(server).getSession(this.getClass().getSimpleName());
		controlSession = allSessionPools.getServerSessionPool(server).getSession(this.getClass().getSimpleName());
		SSHExecuter sshExecuter = (SSHExecuter) session.getCommandExecuter();
		sshExecuter.getInputFromSSHReader().setLogFine(true);
		sshExecuter.getInputFromSSHReader().setLogFineFilter(true);
		System.out.println("FINISHED setup()");
	}
	
	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown()");
		if (session != null) {
			new ProcessKiller().killSubProcesses(server, session.getBashProcessId(), "ProcessKillerTest.tearDown");
		}
		allSessionPools.closeAllFinishedSessionsOnAllServers();
		SSHExecuter sshExecuter = (SSHExecuter) session.getCommandExecuter();
		sshExecuter.getInputFromSSHReader().setLogFine(true);
		sshExecuter.getInputFromSSHReader().setLogFineFilter(true);
	}
	
	/** In this test the parent process has three children. */
	@Test
	public void getChildrenOneLevel() {
		String request = "echo blah1 | sleep 20 | cat | cat &";
		Command command = new Command(request);
		CommandResult commandResult = session.executeCommand(command);
		assertEquals(CommandStatus.FINISHED, commandResult.getCommandStatus());
		ProcessKiller processKiller = new ProcessKiller();
		ArrayList<Integer> childProcesses = processKiller.getChildren(session.getBashProcessId(), controlSession);
		System.out.println("\n\nThe child processes of command \"" + request + "\" are " + childProcesses + "\n\n");
		command = new Command("ps -f");
		System.out.println(controlSession.executeCommand(command).getOutput());
		int remainingProcesses = childProcesses.size();
		assertTrue("Expected 3 or 4 processes but got ", remainingProcesses > 2 &&  remainingProcesses < 5);
	}

	@Test
	public void deepestNodesOfProcessTreeReturnedFirst() {
		ProcessKiller processKiller = new ProcessKiller();
		MockChildProcessFinder mockChildProcessFinder = new MockChildProcessFinder();
		processKiller.setChildProcessFinder(mockChildProcessFinder);
		ArrayList<Integer> childProcesses = processKiller.getChildren(1, null);
		assertEquals("[121, 1122, 1121, 112, 111, 12, 11]", childProcesses.toString());
		// this means 121 is killed first, it is the only child of 12
		// next 1122 and 1121 are killed, they are the children of 112
		mockChildProcessFinder.tree = mockChildProcessFinder.mirrorImageTree;
		childProcesses = processKiller.getChildren(1, null);
		assertEquals("[1212, 1211, 122, 121, 111, 12, 11]", childProcesses.toString());
	}
	
	class MockChildProcessFinder extends ChildProcessFinder {
		int[][] tree = {
				{1,11,12},
				{11,111,112}, // children of node 11 are 111 and 112
				{112,1121,1122},
				{12,121}
		};
		int[][] mirrorImageTree = {
				{1, 11, 12},
				{11, 111},
				{12, 121, 122},
				{121, 1211, 1212}
		};
		@Override
		ArrayList<Integer> getChildren(int parentProcessId, Session controlSession) {
			ArrayList<Integer> children = new ArrayList<Integer>();
			for (int[] nonTerminal : tree) {
				if (nonTerminal[0] == parentProcessId) {
					for (int x=1; x < nonTerminal.length; x++) {
						children.add(nonTerminal[x]);
					}
					break;
				}
			}
			return children;
		}
	}
}
