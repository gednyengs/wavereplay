package com
package example

import com.sekekama.wavereplay._

object WaveformData {

    // Create waveform data
    // Note:
    // - Signal values are provided in value-change format.
    //   This means we specify only those instances where data changes from its
    //   previous value
    //

    // Value-change for CLK signal
    val clk_data: List[WaveEntry] = List(
        (0,  0),
        (10, 1), (15, 0), (20, 1), (25, 0), (30, 1), (35, 0), (40, 1), (45, 0),
        (50, 1), (55, 0), (60, 1), (65, 0), (70, 1), (75, 0), (80, 1), (85, 0),
        (90, 1), (95, 0), (100, 1), (105, 0), (110, 1), (115, 0), (120, 1), (125, 0),
        (130, 1), (135, 0), (140, 1), (145, 0), (150, 1), (155, 0), (160, 1), (165, 0),
        (170, 1), (175, 0), (180, 1), (185, 0), (190, 1), (195, 0), (200, 1), (205, 0)
    )

    // Value-change for WR_READY signal
    val wr_ready_data: List[WaveEntry] = List((0,1))

    // Value-change for WR_VALID signal
    val wr_valid_data: List[WaveEntry] = List(
        (0, 0), (20, 1), (30, 0), (60, 1), (70, 0), (100, 1), (110, 0),
        (150, 1), (160, 0), (180, 1), (190, 0)
    )

    // Value-change for WR_DATA signal
    val wr_data_data: List[WaveEntry] = List(
        (0, 0), (20, 100), (60, 200), (100, 300), (150, 400), (180, 500)
    )

    // Value-change for RD_READY signal
    val rd_ready_data: List[WaveEntry] = List((0,1))

    // Value-change for RD_VALID signal
    val rd_valid_data: List[WaveEntry] = List(
        (0, 0), (30, 1), (40, 0), (100, 1), (110, 0), (160, 1), (170, 0), (190, 1), (200, 0)
    )

    // Value-change for RD_DATA signal
    val rd_data_data: List[WaveEntry] = List(
        (0, 0), (30, 100), (100, 200), (160, 300), (190, 400)
    )

    // Map of signal names to their respective value-change data
    val wvfm_data = Map(
        "CLK"       -> clk_data,
        "WR_READY"  -> wr_ready_data,
        "WR_VALID"  -> wr_valid_data,
        "WR_DATA"   -> wr_data_data,
        "RD_READY"  -> rd_ready_data,
        "RD_VALID"  -> rd_valid_data,
        "RD_DATA"   -> rd_data_data
    )

    // Waveform
    //
    // Note: we use the CursoredWaveform wrapper to enable using time cursors
    val wvfm = CursoredWaveform(DictWaveform(0, 210, 10, wvfm_data))

    // Create signal references so we can use them in expressions
    val clk         = wvfm("CLK")
    val wr_ready    = wvfm("WR_READY")
    val wr_valid    = wvfm("WR_VALID")
    val wr_data     = wvfm("WR_DATA")
    val rd_ready    = wvfm("RD_READY")
    val rd_valid    = wvfm("RD_VALID")
    val rd_data     = wvfm("RD_DATA")
}
