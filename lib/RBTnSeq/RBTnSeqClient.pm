package RBTnSeq::RBTnSeqClient;

use JSON::RPC::Client;
use POSIX;
use strict;
use Data::Dumper;
use URI;
use Bio::KBase::Exceptions;
my $get_time = sub { time, 0 };
eval {
    require Time::HiRes;
    $get_time = sub { Time::HiRes::gettimeofday() };
};

use Bio::KBase::AuthToken;

# Client version should match Impl version
# This is a Semantic Version number,
# http://semver.org
our $VERSION = "0.1.0";

=head1 NAME

RBTnSeq::RBTnSeqClient

=head1 DESCRIPTION


A KBase module: RBTnSeq
This module wraps Morgan Price's RBTnSeq pipeline,
available from https://bitbucket.org/berkeleylab/feba


=cut

sub new
{
    my($class, $url, @args) = @_;
    

    my $self = {
	client => RBTnSeq::RBTnSeqClient::RpcClient->new,
	url => $url,
	headers => [],
    };

    chomp($self->{hostname} = `hostname`);
    $self->{hostname} ||= 'unknown-host';

    #
    # Set up for propagating KBRPC_TAG and KBRPC_METADATA environment variables through
    # to invoked services. If these values are not set, we create a new tag
    # and a metadata field with basic information about the invoking script.
    #
    if ($ENV{KBRPC_TAG})
    {
	$self->{kbrpc_tag} = $ENV{KBRPC_TAG};
    }
    else
    {
	my ($t, $us) = &$get_time();
	$us = sprintf("%06d", $us);
	my $ts = strftime("%Y-%m-%dT%H:%M:%S.${us}Z", gmtime $t);
	$self->{kbrpc_tag} = "C:$0:$self->{hostname}:$$:$ts";
    }
    push(@{$self->{headers}}, 'Kbrpc-Tag', $self->{kbrpc_tag});

    if ($ENV{KBRPC_METADATA})
    {
	$self->{kbrpc_metadata} = $ENV{KBRPC_METADATA};
	push(@{$self->{headers}}, 'Kbrpc-Metadata', $self->{kbrpc_metadata});
    }

    if ($ENV{KBRPC_ERROR_DEST})
    {
	$self->{kbrpc_error_dest} = $ENV{KBRPC_ERROR_DEST};
	push(@{$self->{headers}}, 'Kbrpc-Errordest', $self->{kbrpc_error_dest});
    }

    #
    # This module requires authentication.
    #
    # We create an auth token, passing through the arguments that we were (hopefully) given.

    {
	my $token = Bio::KBase::AuthToken->new(@args);
	
	if (!$token->error_message)
	{
	    $self->{token} = $token->token;
	    $self->{client}->{token} = $token->token;
	}
    }

    my $ua = $self->{client}->ua;	 
    my $timeout = $ENV{CDMI_TIMEOUT} || (30 * 60);	 
    $ua->timeout($timeout);
    bless $self, $class;
    #    $self->_validate_version();
    return $self;
}




=head2 runTnSeq

  $output = $obj->runTnSeq($input)

=over 4

=item Parameter and return types

=begin html

<pre>
$input is a RBTnSeq.TnSeqInput
$output is a RBTnSeq.TnSeqOutput
TnSeqInput is a reference to a hash where the following keys are defined:
	ws has a value which is a string
	input_read_library has a value which is a string
	input_genome has a value which is a string
	input_barcode_model has a value which is a string
	input_minN has a value which is an int
	output_mapped_reads has a value which is a string
	output_pool has a value which is a string
TnSeqOutput is a reference to a hash where the following keys are defined:
	report_name has a value which is a string
	report_ref has a value which is a string

</pre>

=end html

=begin text

$input is a RBTnSeq.TnSeqInput
$output is a RBTnSeq.TnSeqOutput
TnSeqInput is a reference to a hash where the following keys are defined:
	ws has a value which is a string
	input_read_library has a value which is a string
	input_genome has a value which is a string
	input_barcode_model has a value which is a string
	input_minN has a value which is an int
	output_mapped_reads has a value which is a string
	output_pool has a value which is a string
TnSeqOutput is a reference to a hash where the following keys are defined:
	report_name has a value which is a string
	report_ref has a value which is a string


=end text

=item Description

runs TnSeq part of pipeline

=back

=cut

 sub runTnSeq
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function runTnSeq (received $n, expecting 1)");
    }
    {
	my($input) = @args;

	my @_bad_arguments;
        (ref($input) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"input\" (value was \"$input\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to runTnSeq:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'runTnSeq');
	}
    }

    my $result = $self->{client}->call($self->{url}, $self->{headers}, {
	method => "RBTnSeq.runTnSeq",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'runTnSeq',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method runTnSeq",
					    status_line => $self->{client}->status_line,
					    method_name => 'runTnSeq',
				       );
    }
}
 


=head2 makeTnSeqPool

  $output = $obj->makeTnSeqPool($input)

=over 4

=item Parameter and return types

=begin html

<pre>
$input is a RBTnSeq.TnSeqPoolInput
$output is a RBTnSeq.TnSeqOutput
TnSeqPoolInput is a reference to a hash where the following keys are defined:
	ws has a value which is a string
	input_mapped_reads has a value which is a string
	input_minN has a value which is an int
	output_pool has a value which is a string
TnSeqOutput is a reference to a hash where the following keys are defined:
	report_name has a value which is a string
	report_ref has a value which is a string

</pre>

=end html

=begin text

$input is a RBTnSeq.TnSeqPoolInput
$output is a RBTnSeq.TnSeqOutput
TnSeqPoolInput is a reference to a hash where the following keys are defined:
	ws has a value which is a string
	input_mapped_reads has a value which is a string
	input_minN has a value which is an int
	output_pool has a value which is a string
TnSeqOutput is a reference to a hash where the following keys are defined:
	report_name has a value which is a string
	report_ref has a value which is a string


=end text

=item Description

runs TnSeq part of pipeline

=back

=cut

 sub makeTnSeqPool
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function makeTnSeqPool (received $n, expecting 1)");
    }
    {
	my($input) = @args;

	my @_bad_arguments;
        (ref($input) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"input\" (value was \"$input\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to makeTnSeqPool:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'makeTnSeqPool');
	}
    }

    my $result = $self->{client}->call($self->{url}, $self->{headers}, {
	method => "RBTnSeq.makeTnSeqPool",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'makeTnSeqPool',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method makeTnSeqPool",
					    status_line => $self->{client}->status_line,
					    method_name => 'makeTnSeqPool',
				       );
    }
}
 


=head2 getEssentialGenes

  $output = $obj->getEssentialGenes($input)

=over 4

=item Parameter and return types

=begin html

<pre>
$input is a RBTnSeq.EssentialGenesInput
$output is a RBTnSeq.EssentialGenesOutput
EssentialGenesInput is a reference to a hash where the following keys are defined:
	ws has a value which is a string
	input_pool has a value which is a string
	output_feature_set has a value which is a string
EssentialGenesOutput is a reference to a hash where the following keys are defined:
	output_feature_set has a value which is a RBTnSeq.ws_featureset_id
	report_name has a value which is a string
	report_ref has a value which is a string
ws_featureset_id is a string

</pre>

=end html

=begin text

$input is a RBTnSeq.EssentialGenesInput
$output is a RBTnSeq.EssentialGenesOutput
EssentialGenesInput is a reference to a hash where the following keys are defined:
	ws has a value which is a string
	input_pool has a value which is a string
	output_feature_set has a value which is a string
EssentialGenesOutput is a reference to a hash where the following keys are defined:
	output_feature_set has a value which is a RBTnSeq.ws_featureset_id
	report_name has a value which is a string
	report_ref has a value which is a string
ws_featureset_id is a string


=end text

=item Description

gets list of essential (un-hit) genes from a Pool

=back

=cut

 sub getEssentialGenes
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 1)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function getEssentialGenes (received $n, expecting 1)");
    }
    {
	my($input) = @args;

	my @_bad_arguments;
        (ref($input) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"input\" (value was \"$input\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to getEssentialGenes:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'getEssentialGenes');
	}
    }

    my $result = $self->{client}->call($self->{url}, $self->{headers}, {
	method => "RBTnSeq.getEssentialGenes",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'getEssentialGenes',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method getEssentialGenes",
					    status_line => $self->{client}->status_line,
					    method_name => 'getEssentialGenes',
				       );
    }
}
 


=head2 version

  $version = $obj->version()

=over 4

=item Parameter and return types

=begin html

<pre>
$version is a string

</pre>

=end html

=begin text

$version is a string


=end text

=item Description

returns version number of service

=back

=cut

 sub version
{
    my($self, @args) = @_;

# Authentication: none

    if ((my $n = @args) != 0)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function version (received $n, expecting 0)");
    }

    my $result = $self->{client}->call($self->{url}, $self->{headers}, {
	method => "RBTnSeq.version",
	params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'version',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method version",
					    status_line => $self->{client}->status_line,
					    method_name => 'version',
				       );
    }
}
 
  

sub version {
    my ($self) = @_;
    my $result = $self->{client}->call($self->{url}, $self->{headers}, {
        method => "RBTnSeq.version",
        params => [],
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(
                error => $result->error_message,
                code => $result->content->{code},
                method_name => 'version',
            );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(
            error => "Error invoking method version",
            status_line => $self->{client}->status_line,
            method_name => 'version',
        );
    }
}

sub _validate_version {
    my ($self) = @_;
    my $svr_version = $self->version();
    my $client_version = $VERSION;
    my ($cMajor, $cMinor) = split(/\./, $client_version);
    my ($sMajor, $sMinor) = split(/\./, $svr_version);
    if ($sMajor != $cMajor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Major version numbers differ.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor < $cMinor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Client minor version greater than Server minor version.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor > $cMinor) {
        warn "New client version available for RBTnSeq::RBTnSeqClient\n";
    }
    if ($sMajor == 0) {
        warn "RBTnSeq::RBTnSeqClient version is $svr_version. API subject to change.\n";
    }
}

=head1 TYPES



=head2 TnSeqInput

=over 4



=item Description

Inputs to TnSeq part of pipeline.
This should be split into 2 methods or run as an app


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
ws has a value which is a string
input_read_library has a value which is a string
input_genome has a value which is a string
input_barcode_model has a value which is a string
input_minN has a value which is an int
output_mapped_reads has a value which is a string
output_pool has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
ws has a value which is a string
input_read_library has a value which is a string
input_genome has a value which is a string
input_barcode_model has a value which is a string
input_minN has a value which is an int
output_mapped_reads has a value which is a string
output_pool has a value which is a string


=end text

=back



=head2 TnSeqOutput

=over 4



=item Description

Output from TnSeq is just a string report


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
report_name has a value which is a string
report_ref has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
report_name has a value which is a string
report_ref has a value which is a string


=end text

=back



=head2 TnSeqPoolInput

=over 4



=item Description

Inputs to makeTnSeqPool


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
ws has a value which is a string
input_mapped_reads has a value which is a string
input_minN has a value which is an int
output_pool has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
ws has a value which is a string
input_mapped_reads has a value which is a string
input_minN has a value which is an int
output_pool has a value which is a string


=end text

=back



=head2 EssentialGenesInput

=over 4



=item Description

Inputs to getEssentialGenes


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
ws has a value which is a string
input_pool has a value which is a string
output_feature_set has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
ws has a value which is a string
input_pool has a value which is a string
output_feature_set has a value which is a string


=end text

=back



=head2 ws_featureset_id

=over 4



=item Description

The workspace ID of a FeatureSet data object.
@id ws KBaseCollections.FeatureSet


=item Definition

=begin html

<pre>
a string
</pre>

=end html

=begin text

a string

=end text

=back



=head2 EssentialGenesOutput

=over 4



=item Description

getEssentialGenes outputs a report and a FeatureSet


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
output_feature_set has a value which is a RBTnSeq.ws_featureset_id
report_name has a value which is a string
report_ref has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
output_feature_set has a value which is a RBTnSeq.ws_featureset_id
report_name has a value which is a string
report_ref has a value which is a string


=end text

=back



=cut

package RBTnSeq::RBTnSeqClient::RpcClient;
use base 'JSON::RPC::Client';
use POSIX;
use strict;

#
# Override JSON::RPC::Client::call because it doesn't handle error returns properly.
#

sub call {
    my ($self, $uri, $headers, $obj) = @_;
    my $result;


    {
	if ($uri =~ /\?/) {
	    $result = $self->_get($uri);
	}
	else {
	    Carp::croak "not hashref." unless (ref $obj eq 'HASH');
	    $result = $self->_post($uri, $headers, $obj);
	}

    }

    my $service = $obj->{method} =~ /^system\./ if ( $obj );

    $self->status_line($result->status_line);

    if ($result->is_success) {

        return unless($result->content); # notification?

        if ($service) {
            return JSON::RPC::ServiceObject->new($result, $self->json);
        }

        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    elsif ($result->content_type eq 'application/json')
    {
        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    else {
        return;
    }
}


sub _post {
    my ($self, $uri, $headers, $obj) = @_;
    my $json = $self->json;

    $obj->{version} ||= $self->{version} || '1.1';

    if ($obj->{version} eq '1.0') {
        delete $obj->{version};
        if (exists $obj->{id}) {
            $self->id($obj->{id}) if ($obj->{id}); # if undef, it is notification.
        }
        else {
            $obj->{id} = $self->id || ($self->id('JSON::RPC::Client'));
        }
    }
    else {
        # $obj->{id} = $self->id if (defined $self->id);
	# Assign a random number to the id if one hasn't been set
	$obj->{id} = (defined $self->id) ? $self->id : substr(rand(),2);
    }

    my $content = $json->encode($obj);

    $self->ua->post(
        $uri,
        Content_Type   => $self->{content_type},
        Content        => $content,
        Accept         => 'application/json',
	@$headers,
	($self->{token} ? (Authorization => $self->{token}) : ()),
    );
}



1;
