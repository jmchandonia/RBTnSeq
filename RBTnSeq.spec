/*
A KBase module: RBTnSeq
This module wraps Morgan Price's RBTnSeq pipeline,
available from https://bitbucket.org/berkeleylab/feba
*/

module RBTnSeq {
        /* Inputs to TnSeq part of pipeline.
           This should be split into 2 methods or run as an app */
	typedef structure {
	    string ws;
            string input_read_library;
            string input_genome;
            string input_barcode_model;
            int input_minN;
            string output_mapped_reads;
            string output_pool;
	} TnSeqInput;

	/* runs TnSeq part of pipeline */
	funcdef runTnSeq(TnSeqInput input_params) returns (string report) authentication required;

       /* returns version number of service */
       funcdef version() returns (string version);
};
