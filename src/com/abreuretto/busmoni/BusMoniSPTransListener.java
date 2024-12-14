package com.abreuretto.busmoni;

import java.util.List;



public interface BusMoniSPTransListener {
    public void onBusMoniSPTransComplete(List<BusMoni> data);
    public void onBusMoniSPTransFailure(String msg);
}
