function [ noUse ] = echo( )
  noUse = 0;
  fileId = fopen('ECHOtimes.txt','r');
  y = fscanf(fileId, '%i');
  fileId = fopen('ECHOpackets.txt','r');
  x = fscanf(fileId, '%i');
  x = x';
  y = y';
  
  plot(x,y);
  title('ECHO packets','FontSize', 30);
  xlabel('Number of packets','FontSize', 20);
  ylabel({'Response time of packets','in milliseconds'},'FontSize', 20);
  axis([size(x) max(y)/2 (max(y)+max(y)/16)]);
endfunction
