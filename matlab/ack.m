function [ noUse ] = arq( )
  noUse = 0;
  fileId = fopen('ARQtimes.txt','r');
  y = fscanf(fileId, '%i');
  fileId = fopen('ARQpackets.txt','r');
  x = fscanf(fileId, '%i');
  x = x';
  y = y';
  
  plot(x,y);
  xlabel('Number of packets');
  ylabel({'Response time of packets','milliseconds'});
  axis([0 max(y)/2 (max(y)+max(y)/16)]);
endfunction
