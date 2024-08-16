/*Implement Dijkstras Shortest path algorithm in c++ single source to all destination,
user will specify source node then shortest path to all nodes*/
#include<iostream>
using namespace std;
#define INF 999
int v,src ,cost[100][100];
int dist[100];
bool visited[100] = {0}; 
int parent[100];
void init(){
    for (int i=0;i<v;i++){
        parent[i] = i;
        dist[i]= INF;
    }
    dist[src]=0;
}
int getNearestNode(){
    int MinValue = INF;
    int MinNode = 0;
    for(int i=0;i<v;i++){
        if (!visited[i] && dist[i]< MinValue){
            MinValue = dist[i];
            MinNode = i;
        }
    }
    return MinNode;
}

void dijkstra(){
    for(int i=0;i<v;i++){
        int nearest = getNearestNode();
        cout<<"current node "<<nearest<<" : ";
        visited[nearest] = true;

        for(int adj = 0; adj < v ; adj++){
            if(cost[nearest][adj] != INF && dist[adj]>dist[nearest]+cost[nearest][adj]){
                dist[adj] = dist[nearest]+cost[nearest][adj];
                cout<<" neighbor node "<<adj << "\t"<<"updated distance "<<dist[adj] <<", ";
                parent[adj] = nearest;
            }
        }
        cout<<endl;
    }
}

void display(){
    cout<<"Node:\t\t\tCost :\t\t\tPath\n ";
    for(int i = 0;i<v;i++){
        cout<<i<<"\t\t\t"<<dist[i]<<"\t\t\t"<<" ";
        cout<< i <<" ";
        int parnode = parent[i];
        while(parnode != src){
            cout<<" <- "<< parnode << " ";
            parnode = parent[parnode];
        }
        cout<<endl;
    }
}

int main(){
    cout<<"Enter the number of vertices : ";
    cin>>v;
    cout<<"Enter the costs:\n";
    for(int i=0 ; i<v ; i++){
        for(int j=0 ; j<v ; j++){
            cin>>cost[i][j];
        }
    }
    cout<<"Enter source node: ";
    cin>>src;
    init();
    dijkstra();
    display();
}

/*
r/c      0   1   2   3   4   5   6   7   8
0        0   4   999 999 999 999 999 8   999
1        4   0   8   999 999 999 999 8   999
2        999 8   0   7   999 4   999 999 2
3        999 999 7   0   9   14  999 999 999
4        999 999 999 9   0   10  999 999 999
5        999 999 4   14  10  0   2   999 999
6        999 999 999 999 999 2   0   1   6
7        8   11  999 999 999 999 1   0   7
8        999 999 2   999 999 999 6   7   0 
*/
