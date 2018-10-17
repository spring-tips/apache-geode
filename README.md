# features
- caching 
- continuous/query 
- SQL like language called OQL (not SQLFire!)
- PCC: Pivotal Cloud Cache (gemfire cluster provisioned by BOSH)
- WAN replication 
- afaik Geode & Gemfire are the same shit 

# client 
a client is an app that connects to the cluster by resolving a cluster member from a locator.  u give it a list of 1+ locators, and it finds a cluster members and from there it no longer needs the locator. 

# cluster
is a collection of servers that communicate to maintain cluster integrity and data integrity 

# server 
a member of a cluster that manages data. they contain regions. 

# region 
a region is a `Map<K,V>` that's distrbuted across the server nodes. a region might be called 'people', but u might have keys like the SSN and value associated with that persons profile info. Both keys and values are objects. any arbitrary java object.

# serialization 
data serialization supports auto serialization by default. this doesn't need to be `Serializable`. You can alternatively use PDX. the Geode proprietary encoding. PDX is x-platform. It also lets u run queries that dont unpack the whole object to read a value. Ur beans can also implement PdxSerializable or DataSerializable. You have to implement the serialize and deserialize methods. U can also just write ur own PdxSerializer or DataSerializer and tell the cluster to use those impls to handle serialization.  

# locator 
is a special member of the clsuter used to detect the clients. provides discovery and load balancing services. u usually want around 3 locators.	

# membership protocol 
each node asks the node to the right if its alive. if it deostn respond, then the asking node informs the lead node that it hasnt responded. all members i the cluster have a membership view.

# functions
java code that executes inside a Geode server. 

# useful gfsh queries 
> query --query="select id, temp  from /temps"
> query --query="select id, temp , city.substring(0,3)  from /temps" 
> 
