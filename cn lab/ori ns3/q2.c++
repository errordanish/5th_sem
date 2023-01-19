#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"
#include "ns3/traffic-control-module.h"
#include "ns3/flow-monitor-module.h"


using namespace ns3;

NS_LOG_COMPONENT_DEFINE ("TrafficControlExample");

/**************void hatane hain*****************************/////////////

/*void
TcPacketsInQueueTrace (uint32_t oldValue, uint32_t newValue)
{
  std::cout << "TcPacketsInQueue " << oldValue << " to " << newValue << std::endl;
}

void
DevicePacketsInQueueTrace (uint32_t oldValue, uint32_t newValue)
{
  std::cout << "DevicePacketsInQueue " << oldValue << " to " << newValue << std::endl;
}

void
SojournTimeTrace (Time sojournTime)
{
  std::cout << "Sojourn time " << sojournTime.ToDouble (Time::MS) << "ms" << std::endl;
}*/

int main (int argc, char *argv[])
{
  double simulationTime = 10; //seconds
  std::string transportProt = "Udp";         /*2 baar karna hain pehle udp fir tcp*/
  std::string socketType;
 
/*command line hata hain*/
 /* CommandLine cmd;
  cmd.AddValue ("transportProt", "Transport protocol to use: Tcp, Udp", transportProt);
  cmd.Parse (argc, argv);*/

  if (transportProt.compare ("Tcp") == 0)
    {
      socketType = "ns3::TcpSocketFactory";
    }
  else
    {
      socketType = "ns3::UdpSocketFactory";
    }

  NodeContainer nodes;
  nodes.Create (4);     /*4 nodes rkhna hain*/

  PointToPointHelper pointToPoint;
  pointToPoint.SetDeviceAttribute ("DataRate", StringValue ("10Mbps"));
  pointToPoint.SetChannelAttribute ("Delay", StringValue ("2ms"));
  pointToPoint.SetQueue ("ns3::DropTailQueue", "MaxSize", StringValue ("1p"));

  NetDeviceContainer devices02=pointToPoint.Install (nodes.Get(0),nodes.Get(2));
 NetDeviceContainer devices12=pointToPoint.Install (nodes.Get(1),nodes.Get(2));
 NetDeviceContainer devices23=pointToPoint.Install (nodes.Get(2),nodes.Get(3));

  InternetStackHelper stack;
  stack.Install (nodes);



/*traffic control se hatana hain Ipv4 address helper tk*/
 /* TrafficControlHelper tch;
  tch.SetRootQueueDisc ("ns3::RedQueueDisc");
  QueueDiscContainer qdiscs = tch.Install (devices);

  Ptr<QueueDisc> q = qdiscs.Get (1);
  q->TraceConnectWithoutContext ("PacketsInQueue", MakeCallback (&TcPacketsInQueueTrace));
  Config::ConnectWithoutContext ("/NodeList/1/$ns3::TrafficControlLayer/RootQueueDiscList/0/SojournTime",
                                 MakeCallback (&SojournTimeTrace));

  Ptr<NetDevice> nd = devices.Get (1);
  Ptr<PointToPointNetDevice> ptpnd = DynamicCast<PointToPointNetDevice> (nd);
  Ptr<Queue<Packet> > queue = ptpnd->GetQueue ();
  queue->TraceConnectWithoutContext ("PacketsInQueue", MakeCallback (&DevicePacketsInQueueTrace));*/


/* interfaces and devices ko change krna hain topology k according*/
  Ipv4AddressHelper address;
  address.SetBase ("10.1.1.0", "255.255.255.0");

  Ipv4InterfaceContainer interfaces02 = address.Assign (devices02);
address.SetBase ("10.1.2.0", "255.255.255.0");

  Ipv4InterfaceContainer interfaces12 = address.Assign (devices12);
address.SetBase ("10.1.3.0", "255.255.255.0");

  Ipv4InterfaceContainer interfaces23 = address.Assign (devices23);

/*routing table daalni hain third.cc se copy kr k */
Ipv4GlobalRoutingHelper::PopulateRoutingTables ();

  //Flow
  uint16_t port = 7;
  Address localAddress (InetSocketAddress (Ipv4Address::GetAny (), port));
  PacketSinkHelper packetSinkHelper (socketType, localAddress);
  ApplicationContainer sinkApp = packetSinkHelper.Install (nodes.Get (3));/*sink 3 hain isliye*/

  sinkApp.Start (Seconds (0.0));
  sinkApp.Stop (Seconds (simulationTime + 0.1));

  uint32_t payloadSize = 1448;
  Config::SetDefault ("ns3::TcpSocket::SegmentSize", UintegerValue (payloadSize));

  OnOffHelper onoff (socketType, Ipv4Address::GetAny ());
  onoff.SetAttribute ("OnTime",  StringValue ("ns3::ConstantRandomVariable[Constant=1]"));
  onoff.SetAttribute ("OffTime", StringValue ("ns3::ConstantRandomVariable[Constant=0]"));
  onoff.SetAttribute ("PacketSize", UintegerValue (payloadSize));
  onoff.SetAttribute ("DataRate", StringValue ("50Mbps")); //bit/s
  ApplicationContainer apps;

  InetSocketAddress rmt (interfaces23.GetAddress (1), port); /*rmt ko 1 karna hain interface23 rahenge*/
  rmt.SetTos (0xb8);
  AddressValue remoteAddress (rmt);
  onoff.SetAttribute ("Remote", remoteAddress);
  apps.Add (onoff.Install (nodes.Get (1)));
  apps.Start (Seconds (1.0));
  apps.Stop (Seconds (simulationTime + 0.1));

  FlowMonitorHelper flowmon;
  Ptr<FlowMonitor> monitor = flowmon.InstallAll();

  Simulator::Stop (Seconds (simulationTime + 5));
  Simulator::Run ();

  Ptr<Ipv4FlowClassifier> classifier = DynamicCast<Ipv4FlowClassifier> (flowmon.GetClassifier ());
  std::map<FlowId, FlowMonitor::FlowStats> stats = monitor->GetFlowStats ();
  std::cout << std::endl << "*** Flow monitor statistics ***" << std::endl;
/*isko ratt lo for loop ko*/
  for(auto i :stats){
        auto t=classifier->FindFlow(i.first);
        NS_LOG_UNCOND(i.first<<" "<< t.destinationAddress<<" "<<t.sourceAddress);
        NS_LOG_UNCOND(i.second.txPackets);
}

  Simulator::Destroy ();

 
  return 0;
}