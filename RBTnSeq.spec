/*
A KBase module: RBTnSeq
This module wraps Morgan Price's RBTnSeq pipeline,
available from https://bitbucket.org/berkeleylab/feba
*/

module RBTnSeq {
        /* Inputs to TnSeq part of pipeline.
           This needs to be split into 2 steps, pending
           update of some KBaseRBTnSeq objects */
	typedef structure {
	    string ws;
            string read_type;
            string input_read_library;
            string input_genome;
            string sequenced_at;
            string start_date;
            /* string input_barcode_model; is fixed for now */
            string output_pool;
            string output_tnseq_experiment;
            string output_tnseq_library;
	} TnSeqInput;

	/* runs TnSeq part of pipeline */
	funcdef runTnSeq(TnSeqInput input_params) returns (string report) authentication required;
};
