FROM gitpod/workspace-full:2022-07-20-05-50-58

RUN brew install kubectl && \ 
    brew install helm 

# gloud cli
## source: https://cloud.google.com/sdk/docs/install?hl=de#deb
RUN sudo apt-get install -y apt-transport-https ca-certificates gnupg && \
    echo "deb [signed-by=/usr/share/keyrings/cloud.google.gpg] https://packages.cloud.google.com/apt cloud-sdk main" | sudo tee -a /etc/apt/sources.list.d/google-cloud-sdk.list && \
    curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key --keyring /usr/share/keyrings/cloud.google.gpg add - && \
    sudo apt-get update && sudo apt-get install -y google-cloud-cli

# extend bashrc with useful stuff like brew completion
COPY ./.gitpod/bashrc-extend.txt bashrc-extend.txt

RUN cat bashrc-extend.txt >> /home/gitpod/.bashrc
