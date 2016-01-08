FROM kbase/kbase:sdkbase.latest
MAINTAINER John-Marc Chandonia
# -----------------------------------------

# Insert instructions here to install
# any required dependencies for your module.

# get a particular version of FEBA code (for provenance/stability):

WORKDIR /kb/module
RUN git clone https://bitbucket.org/berkeleylab/feba && \
    cd feba && \
    git reset --hard 1b9b2e26fb6eabb575d9b3510c0afbdb1a46d749 

WORKDIR /kb/module/feba/bin
RUN wget http://hgdownload.cse.ucsc.edu/admin/exe/linux.x86_64/blat/blat
RUN chmod 755 blat

# -----------------------------------------

COPY ./ /kb/module
RUN mkdir -p /kb/module/work

WORKDIR /kb/module

RUN make

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]
