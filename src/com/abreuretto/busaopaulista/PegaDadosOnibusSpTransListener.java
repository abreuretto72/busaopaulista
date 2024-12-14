package com.abreuretto.busaopaulista;

import java.util.List;

public interface PegaDadosOnibusSpTransListener {
    public void onPegaDadosOnibusComplete(List<OnibusRoot> data);
    public void onPegaDadosOnibusFailure(String msg);
}

