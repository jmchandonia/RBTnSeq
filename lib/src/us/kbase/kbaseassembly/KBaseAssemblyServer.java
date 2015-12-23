package us.kbase.kbaseassembly;

import us.kbase.common.service.JsonServerServlet;

//BEGIN_HEADER
//END_HEADER

/**
 * <p>Original spec-file module name: KBaseAssembly</p>
 * <pre>
 * </pre>
 */
public class KBaseAssemblyServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;

    //BEGIN_CLASS_HEADER
    //END_CLASS_HEADER

    public KBaseAssemblyServer() throws Exception {
        super("KBaseAssembly");
        //BEGIN_CONSTRUCTOR
        //END_CONSTRUCTOR
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: <program> <server_port>");
            return;
        }
        new KBaseAssemblyServer().startupServer(Integer.parseInt(args[0]));
    }
}
