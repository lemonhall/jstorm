package com.alibaba.jstorm.task.execute;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.ISpout;

import com.alibaba.jstorm.stats.CommonStatsRolling;

/**
 * The action after spout receive one ack tuple
 * 
 * @author yannian/Longda
 * 
 */
public class AckSpoutMsg implements IAckMsg {
	private static Logger LOG = LoggerFactory.getLogger(AckSpoutMsg.class);

	private ISpout spout;
	private Object msg_id;
	private String stream;
	private long time_stamp;
	private CommonStatsRolling task_stats;
	private boolean isDebug = false;

	public AckSpoutMsg(ISpout _spout, Object _msg_id, boolean _isDebug,
			String stream, long _time_stamp, CommonStatsRolling _task_stats) {
		this.spout = _spout;
		this.msg_id = _msg_id;
		this.isDebug = _isDebug;
		this.stream = stream;
		this.time_stamp = _time_stamp;
		this.task_stats = _task_stats;
	}

	public void run() {
		if (isDebug) {
			LOG.info("Acking message {}", msg_id);
		}

		spout.ack(msg_id);

		task_stats.spout_acked_tuple(stream, time_stamp);
	}

}
