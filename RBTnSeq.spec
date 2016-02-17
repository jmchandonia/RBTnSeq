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

	/* Output from TnSeq is just a string report */
	typedef structure {
	    string report_name;
	    string report_ref;
	} TnSeqOutput;

	/* runs TnSeq part of pipeline */
	funcdef runTnSeq(TnSeqInput input) returns (TnSeqOutput output) authentication required;

	/* Inputs to makeTnSeqPool */
	typedef structure {
	    string ws;
            string input_mapped_reads;
            int input_minN;
            string output_pool;
	} TnSeqPoolInput;

	/* runs TnSeq part of pipeline */
	funcdef makeTnSeqPool(TnSeqPoolInput input) returns (TnSeqOutput output) authentication required;

        /* Inputs to getEssentialGenes */
        typedef structure {
	    string ws;
	    string input_pool;
	    string output_feature_set;
	} EssentialGenesInput;
	
        /* 
	    The workspace ID of a FeatureSet data object.
	    @id ws KBaseCollections.FeatureSet
        */
	typedef string ws_featureset_id;

	/* getEssentialGenes outputs a report and a FeatureSet */
	typedef structure {
	    ws_featureset_id output_feature_set;
	    string report_name;
	    string report_ref;
	} EssentialGenesOutput;

        /* gets list of essential (un-hit) genes from a Pool */
        funcdef getEssentialGenes(EssentialGenesInput input) returns (EssentialGenesOutput output) authentication required;

        /* returns version number of service */
        funcdef version() returns (string version);
};
