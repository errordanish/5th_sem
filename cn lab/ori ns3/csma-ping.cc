/* -*- Mode:C++; c-file-style:"gnu"; indent-tabs-mode:nil; -*- */
/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation;
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

// Network topology
//
//       n0    n1   n2   n3
//       |     |    |    |
//     =====================
//
//  node n0,n1,n3 pings to node n2
//  node n0 generates protocol 2 (IGMP) to node n3

#include <iostream>
#include <fstream>
#include <string>
#include <cassert>

#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/csma-module.h"
#include "ns3/applications-module.h"
#include "ns3/internet-apps-module.h"
#include "ns3/internet-module.h"
#include "ns3/flow-monitor-module.h"      /***********header file daalni hain yeh waali************************************/

using namespace ns3;

NS_LOG_COMPONENT_DEFINE ("CsmaPingExample");

/*void hatao saare***************************************/

/*static void SinkRx (Ptr<const Packet> p, const Address &ad)
{
  //std::cout << *p << std::endl;
}

static void PingRtt (std::string context, Time rtt)
{
  //std::cout << context << " " << rtt << std::endl;
}*/

int
main (int argc, char *argv[])
{

  CommandLine cmd;
  cmd.Parse (argc, argv);

  // Here, we will explicitly create four nodes.
  NS_LOG_INFO ("Create nodes.");
  NodeContainer c;
  c.Create (4);

  // connect all our nodes to a shared channel.
  NS_LOG_INFO ("Build Topology.");
  CsmaHelper csma;
/**********data rate ko double kr do mtlb multiply by 2*******************/
  csma.SetChannelAttribute ("DataRate", DataRateValue (DataRate (10000000)));
  csma.SetChannelAttribute ("Delay", TimeValue (MilliSeconds (2)));
  csma.SetDeviceAttribute ("EncapsulationMode", StringValue ("Llc"));
  NetDeviceContainer devs = csma.Install (c);

  // add an ip stack to all nodes.
  NS_LOG_INFO ("Add ip stack.");
  InternetStackHelper ipStack;
  ipStack.Install (c);

  // assign ip addresses
  NS_LOG_INFO ("Assign ip addresses.");
  Ipv4AddressHelper ip;
  ip.SetBase ("192.168.1.0", "255.255.255.0");
  Ipv4InterfaceContainer addresses = ip.Assign (devs);


/*****************create source se create pinger tk comment kr do***************/
  
/*NS_LOG_INFO ("Create Source");
  Config::SetDefault ("ns3::Ipv4RawSocketImpl::Protocol", StringValue ("2"));
  InetSocketAddress dst = InetSocketAddress (addresses.GetAddress (3));
  OnOffHelper onoff = OnOffHelper ("ns3::Ipv4RawSocketFactory", dst);
  onoff.SetConstantRate (DataRate (15000));
  onoff.SetAttribute ("PacketSize", UintegerValue (1200));


  ApplicationContainer apps = onoff.Install (c.Get (0));
  apps.Start (Seconds (1.0));
  apps.Stop (Seconds (10.0));

  NS_LOG_INFO ("Create Sink.");
  PacketSinkHelper sink = PacketSinkHelper ("ns3::Ipv4RawSocketFactory", dst);
  apps = sink.Install (c.Get (3));
  apps.Start (Seconds (0.0));
  apps.Stop (Seconds (11.0));
*/



/*fow copy krna hain traffic control se app.stop tk*****************/
/*******socket type ko "ns3::UdpSocketFactory" krna hain simulation time ko 10 nodes.get(0)ko c.Get(0)  interfaces ki jagah addresses krna hain      *******/

uint16_t port = 7;
  Address localAddress (InetSocketAddress (Ipv4Address::GetAny (), port));
  PacketSinkHelper packetSinkHelper ("ns3::UdpSocketFactory", localAddress);
  ApplicationContainer sinkApp = packetSinkHelper.Install (c.Get (0));

  sinkApp.Start (Seconds (0.0));
  sinkApp.Stop (Seconds (10 + 0.1));

  uint32_t payloadSize = 1448;
  Config::SetDefault ("ns3::TcpSocket::SegmentSize", UintegerValue (payloadSize));

  OnOffHelper onoff ("ns3::UdpSocketFactory", Ipv4Address::GetAny ());
  onoff.SetAttribute ("OnTime",  StringValue ("ns3::ConstantRandomVariable[Constant=1]"));
  onoff.SetAttribute ("OffTime", StringValue ("ns3::ConstantRandomVariable[Constant=0]"));
  onoff.SetAttribute ("PacketSize", UintegerValue (payloadSize));
  onoff.SetAttribute ("DataRate", StringValue ("50Mbps")); //bit/s
  ApplicationContainer apps;

  InetSocketAddress rmt (addresses.GetAddress (0), port);
  rmt.SetTos (0xb8);
  AddressValue remoteAddress (rmt);
  onoff.SetAttribute ("Remote", remoteAddress);
  apps.Add (onoff.Install (c.Get (1)));
  apps.Start (Seconds (1.0));
  apps.Stop (Seconds (10 + 0.1));




  NS_LOG_INFO ("Create pinger");
  V4PingHelper ping = V4PingHelper (addresses.GetAddress (2));
  NodeContainer pingers;
  pingers.Add (c.Get (0));
  pingers.Add (c.Get (1));
  pingers.Add (c.Get (3));
  apps = ping.Install (pingers);
  apps.Start (Seconds (2.0));
  apps.Stop (Seconds (5.0));



/*************traffic control k flowmonitor se copy kro destroy tk necessary cheeze cout kro***********************************/

FlowMonitorHelper flowmon;
  Ptr<FlowMonitor> monitor = flowmon.InstallAll();

  Simulator::Stop (Seconds (10 + 5));
  Simulator::Run ();

  Ptr<Ipv4FlowClassifier> classifier = DynamicCast<Ipv4FlowClassifier> (flowmon.GetClassifier ());
  std::map<FlowId, FlowMonitor::FlowStats> stats = monitor->GetFlowStats ();
  std::cout << std::endl << "*** Flow monitor statistics ***" << std::endl;
  std::cout << "  Tx Packets/Bytes:   " << stats[1].txPackets<< " / " << stats[1].txBytes << std::endl;
  std::cout << "  Offered Load: " << stats[1].txBytes * 8.0 / (stats[1].timeLastTxPacket.GetSeconds () - stats[1].timeFirstTxPacket.GetSeconds ()) / 1000000 << " Mbps" << std::endl;
  std::cout << "  Rx Packets/Bytes:   " << stats[1].rxPackets<< " / " << stats[1].rxBytes << std::endl;
  
  std::cout << "  Throughput: " << stats[1].rxBytes * 8.0 / (stats[1].timeLastRxPacket.GetSeconds () - stats[1].timeFirstRxPacket.GetSeconds ()) / 1000000 << " Mbps" << std::endl;
  std::cout << "  Mean delay:   " << stats[1].delaySum.GetSeconds () / stats[1].rxPackets << std::endl;
  std::cout << "  Mean jitter:   " << stats[1].jitterSum.GetSeconds () / (stats[1].rxPackets - 1) << std::endl;


  Simulator::Destroy ();




/*****************comment out*****************************/
  
/*NS_LOG_INFO ("Configure Tracing.");
  // first, pcap tracing in non-promiscuous mode
  csma.EnablePcapAll ("csma-ping", false);

  // then, print what the packet sink receives.
  Config::ConnectWithoutContext ("/NodeList/3/ApplicationList/0/$ns3::PacketSink/Rx", 
                                 MakeCallback (&SinkRx));
  // finally, print the ping rtts.
  Config::Connect ("/NodeList/*//*ApplicationList*//*/$ns3::V4Ping/Rtt",                     /*application list comment out krna hain**************/
                   MakeCallback (&PingRtt));                         

  Packet::EnablePrinting ();


  NS_LOG_INFO ("Run Simulation.");
  Simulator::Run ();
  Simulator::Destroy ();
  NS_LOG_INFO ("Done.");*/
}
