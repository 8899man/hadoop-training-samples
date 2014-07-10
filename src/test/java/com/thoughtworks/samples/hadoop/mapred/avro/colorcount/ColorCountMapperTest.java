package com.thoughtworks.samples.hadoop.mapred.avro.colorcount;

import com.thoughtworks.samples.hadoop.avro.User;
import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

import java.io.IOException;

public class ColorCountMapperTest {

  @Test
  public void should_select_color_with_count_one() throws IOException {

    User user = new User("Hemanth", new Integer(0), "RED");
    AvroKey<User> userAvroKey = new AvroKey<User>(user);
    Job job = AvroJobInitializer.setupAvroSerialization();
    job.getConfiguration().setStrings("avro.serialization.key.writer.schema", user.getSchema().toString(true));

    ColorCountMapper colorCountMapper = new ColorCountMapper();
    MapDriver<AvroKey<User>,NullWritable,Text,IntWritable> mapDriver
      = MapDriver.newMapDriver(colorCountMapper).withConfiguration(job.getConfiguration());
    mapDriver.withInput(userAvroKey, NullWritable.get());
    mapDriver.withOutput(new Text("RED"), new IntWritable(1));

    mapDriver.runTest();
  }

}