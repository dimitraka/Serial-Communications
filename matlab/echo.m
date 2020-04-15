function [ noUse ] = echo( )
  noUse = 0;
  fileId = fopen('ECHOtimes.txt','r');
  y = fscanf(fileId, '%i');
  fileId = fopen('ECHOpackets.txt','r');
  x = fscanf(fileId, '%i');
  x = x';
  y = y';
  
  plot(x,y);
  xlabel('Number of packets');
  ylabel({'Response time of packets','milliseconds'});
  axis([size(x) max(y)/2 (max(y)+max(y)/16)]);
endfunction
