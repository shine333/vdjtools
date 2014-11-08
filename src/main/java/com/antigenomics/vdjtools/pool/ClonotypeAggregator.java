/*
 * Copyright 2013-2014 Mikhail Shugay (mikhail.shugay@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified on 2.11.2014 by mikesh
 */

package com.antigenomics.vdjtools.pool;

import com.antigenomics.vdjtools.Clonotype;

public abstract class ClonotypeAggregator {
    private int sampleId;
    private int incidenceCount, count;
    //private double freq, freqRem = 0;

    public ClonotypeAggregator(Clonotype clonotype, int sampleId) {
        this.incidenceCount = 1;
        //this.freq = Math.log10(clonotype.getFreq());
        this.count = clonotype.getCount();
        this.sampleId = sampleId;
    }

    public final void combine(Clonotype other, int sampleId) {
        if (this.sampleId != sampleId) {
            incidenceCount++;
            this.sampleId = sampleId;
            //freq += freqRem > 0 ? Math.log10(freqRem) : 0;  // add remainder
            //freqRem = other.getFreq();                      // start accumulating freq
        } //else
        //freqRem += other.getFreq();                     // continue accumulating freq

        count += other.getCount();
        _combine(other, sampleId);
    }

    protected abstract boolean _combine(Clonotype other, int sampleId);

    public int getIncidenceCount() {
        return incidenceCount;
    }

    public int getCount() {
        return count;
    }

    /*
    public double getFreqLogSum() {
        return freq + (freqRem > 0 ? Math.log10(freqRem) : 0);
    }

    public double getFreqGeomMean() {
        return Math.pow(10.0, getFreqLogSum() / incidenceCount);
    }

    public double getFreqGeomMean(int numberOfSamples) {
        return Math.pow(10.0, (getFreqLogSum() + (numberOfSamples - incidenceCount) * Misc.JITTER_LOG10) / numberOfSamples);
    }*/
}