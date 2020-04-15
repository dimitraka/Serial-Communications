function [ noUse ] = barChart( )
  noUse = 0;
  fileId = fopen('ARQresent.txt','r');
  y = fscanf(fileId, '%i');
  fileId = fopen('ARQpackets.txt','r');
  x = fscanf(fileId, '%i');
  x = x';
  y = y';
  
  plot(x,y);
  xlabel('Number of packets');
  ylabel({'Response time of resent packets','milliseconds'});
  ylim([0 max(y)]);
  xlim([0 max(x)]);

endfunction
