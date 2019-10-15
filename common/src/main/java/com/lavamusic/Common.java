package com.lavamusic;

public class Common {
    private static Common common;
    private static final Object LockThis = new Object();
    public synchronized static Common object() {
        synchronized (LockThis) {
            if (null == common) {
                common = new Common();
            }
        }
        return common;
    }

    public int getTime(){
        return 0;
    }

    public String getName(){
        return "com.lavamusic.midi.MusicService";
    }

    public static final int jni_tim_none = 0;
    public static final int jni_tim_quit = 1;
    public static final int jni_tim_next = 2;
    public static final int jni_tim_previous = 3;
    public static final int jni_tim_forward = 4;
    public static final int jni_tim_back = 5;
    public static final int jni_tim_jump = 6;
    public static final int jni_tim_toggle_pause = 7;
    public static final int jni_tim_restart = 8;
    public static final int jni_tim_pause = 9;
    public static final int jni_tim_continue = 10;
    public static final int jni_tim_really_previous = 11;
    public static final int jni_tim_change_volume = 12;
    public static final int jni_tim_load_file = 13;
    public static final int jni_tim_tune_end = 14;
    public static final int jni_tim_keyup = 15;
    public static final int jni_tim_keydown = 16;
    public static final int jni_tim_speedup = 17;
    public static final int jni_tim_speeddown = 18;
    public static final int jni_tim_voiceincr = 19;
    public static final int jni_tim_voicedecr = 20;
    public static final int jni_tim_toggle_drumchan = 21;
    public static final int jni_tim_reload = 22;
    public static final int jni_tim_toggle_sndspec = 23;
    public static final int jni_tim_change_rev_effb = 24;
    public static final int jni_tim_change_rev_time = 25;
    public static final int jni_tim_sync_restart = 26;
    public static final int jni_tim_toggle_ctl_speana = 27;
    public static final int jni_tim_change_rate = 28;
    public static final int jni_tim_output_changed = 29;
    public static final int jni_tim_stop = 30;
    public static final int jni_tim_toggle_mute = 31;
    public static final int jni_tim_solo_play = 32;
    public static final int jni_tim_mute_clear = 33;
    public static final int jni_tim_holdmask = 0x800;
    public static final int jni_tim_unholdmask = 0x8000;

    public static final int ta_cmd_error = -5;
    public static final int ta_cmd_gui_play = 0;
    public static final int ta_cmd_refresh_filebrowser = 1;
    public static final int ta_cmd_gui_play_full = 3;
    public static final int ta_cmd_pause_stop = 5;
    public static final int ta_cmd_special_notification_finished = 8;
    public static final int ta_cmd_service_started = 9;

    public static final int msrv_cmd_error = -5;
    public static final int msrv_cmd_load_plist_play = 0;
    public static final int msrv_cmd_play = 1;
    public static final int msrv_cmd_pause = 2;
    public static final int msrv_cmd_stop = 5;
    public static final int msrv_cmd_get_info = 11;
    public static final int msrv_cmd_get_plist = 12;
    public static final int msrv_cmd_play_or_pause = 13;
    public static final int msrv_cmd_write_new = 14;
    public static final int msrv_cmd_write_curr = 15;
    public static final int msrv_cmd_load_cfg = 17;

}
