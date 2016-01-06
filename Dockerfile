FROM kbase/kbase:sdkbase.latest
MAINTAINER KBase Developer
# -----------------------------------------

# Insert apt-get instructions here to install
# any required dependencies for your module.

# RUN apt-get update

WORKDIR /kb/module

# get a particular version of FEBA code (for provenance/stability):

RUN git clone https://bitbucket.org/berkeleylab/feba && \
    cd feba && \
    git reset --hard 1b9b2e26fb6eabb575d9b3510c0afbdb1a46d749

# -----------------------------------------

COPY ./ /kb/module
RUN mkdir -p /kb/module/work

WORKDIR /kb/module

RUN make

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]
